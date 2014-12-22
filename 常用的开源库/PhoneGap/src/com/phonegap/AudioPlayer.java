/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * This class implements the audio playback and recording capabilities used by PhoneGap.
 * It is called by the AudioHandler PhoneGap class.
 * Only one file can be played or recorded per class instance.
 * 
 * Local audio files must reside in one of two places:
 * 		android_asset: 		file name must start with /android_asset/sound.mp3
 * 		sdcard:				file name is just sound.mp3
 */
public class AudioPlayer implements OnCompletionListener, OnPreparedListener, OnErrorListener {

    private static final String LOG_TAG = "AudioPlayer";

    // AudioPlayer states
	private static int MEDIA_NONE = 0;
	private static int MEDIA_STARTING = 1;
	private static int MEDIA_RUNNING = 2;
	private static int MEDIA_PAUSED = 3;
	private static int MEDIA_STOPPED = 4;
	
	// AudioPlayer message ids
	private static int MEDIA_STATE = 1;
	private static int MEDIA_DURATION = 2;
    private static int MEDIA_POSITION = 3;
	private static int MEDIA_ERROR = 9;
	
	// AudioPlayer error codes
	private static int MEDIA_ERROR_PLAY_MODE_SET = 1;
	private static int MEDIA_ERROR_ALREADY_RECORDING = 2;
	private static int MEDIA_ERROR_STARTING_RECORDING = 3;
	private static int MEDIA_ERROR_RECORD_MODE_SET = 4;
	private static int MEDIA_ERROR_STARTING_PLAYBACK = 5;
	private static int MEDIA_ERROR_RESUME_STATE = 6;
	private static int MEDIA_ERROR_PAUSE_STATE = 7;
	private static int MEDIA_ERROR_STOP_STATE = 8;

	private AudioHandler handler;					// The AudioHandler object
	private String id;								// The id of this player (used to identify Media object in JavaScript)
	private int state = MEDIA_NONE;					// State of recording or playback
	private String audioFile = null;				// File name to play or record to
	private float duration = -1;					// Duration of audio

	private MediaRecorder recorder = null;			// Audio recording object
	private String tempFile = null;					// Temporary recording file name
	
	private MediaPlayer mPlayer = null;				// Audio player object
	private boolean prepareOnly = false;

	/**
	 * Constructor.
	 * 
	 * @param handler			The audio handler object
	 * @param id				The id of this audio player
	 */
	public AudioPlayer(AudioHandler handler, String id) {
		this.handler = handler;
		this.id = id;
        this.tempFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmprecording.mp3";
	}	

	/**
	 * Destroy player and stop audio playing or recording.
	 */
	public void destroy() {
		
		// Stop any play or record
		if (this.mPlayer != null) {
	        if ((this.state == MEDIA_RUNNING) || (this.state == MEDIA_PAUSED)) {
	            this.mPlayer.stop();
	            this.setState(MEDIA_STOPPED);
	        }
			this.mPlayer.release();
			this.mPlayer = null;
		}
		if (this.recorder != null) {
			this.stopRecording();
			this.recorder.release();
			this.recorder = null;
		}
	}

	/**
	 * Start recording the specified file.
	 * 
	 * @param file				The name of the file
	 */
	public void startRecording(String file) {
		if (this.mPlayer != null) {
			Log.d(LOG_TAG, "AudioPlayer Error: Can't record in play mode.");
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_PLAY_MODE_SET+");");
		}
		
		// Make sure we're not already recording
		else if (this.recorder == null) {
			this.audioFile = file;
			this.recorder = new MediaRecorder();
			this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			this.recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT); // THREE_GPP);
			this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); //AMR_NB);
			this.recorder.setOutputFile(this.tempFile);
			try {
				this.recorder.prepare();
				this.recorder.start();
				this.setState(MEDIA_RUNNING);
				return;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_STARTING_RECORDING+");");			
		}
		else {
		    Log.d(LOG_TAG, "AudioPlayer Error: Already recording.");
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_ALREADY_RECORDING+");");			
		}
	}
	
	/**
	 * Save temporary recorded file to specified name
	 * 
	 * @param file
	 */
	public void moveFile(String file) {
		
		/* this is a hack to save the file as the specified name */
		File f = new File(this.tempFile);
		f.renameTo(new File("/sdcard/" + file));
	}
	
    /**
     * Stop recording and save to the file specified when recording started.
     */
	public void stopRecording() {
		if (this.recorder != null) {
			try{
				if (this.state == MEDIA_RUNNING) {
					this.recorder.stop();
					this.setState(MEDIA_STOPPED);
				}
				this.moveFile(this.audioFile);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	
    /**
     * Start or resume playing audio file.
     * 
     * @param file				The name of the audio file.
     */
	public void startPlaying(String file) {
		if (this.recorder != null) {
		    Log.d(LOG_TAG, "AudioPlayer Error: Can't play in record mode.");
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_RECORD_MODE_SET+");");
		}
		
		// If this is a new request to play audio, or stopped
		else if ((this.mPlayer == null) || (this.state == MEDIA_STOPPED)) {
			try {
				// If stopped, then reset player
				if (this.mPlayer != null) {
					this.mPlayer.reset();
				}
				// Otherwise, create a new one
				else {
					this.mPlayer = new MediaPlayer();
				}
				this.audioFile = file;
				
				// If streaming file
				if (this.isStreaming(file)) {
					this.mPlayer.setDataSource(file);
					this.mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
					this.setState(MEDIA_STARTING);
					this.mPlayer.setOnPreparedListener(this);		
					this.mPlayer.prepareAsync();
				}
				
				// If local file
				else {
					if (file.startsWith("/android_asset/")) {
						String f = file.substring(15);
						android.content.res.AssetFileDescriptor fd = this.handler.ctx.getBaseContext().getAssets().openFd(f);
						this.mPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
					}
					else {
						this.mPlayer.setDataSource("/sdcard/" + file);
					}
					this.setState(MEDIA_STARTING);
					this.mPlayer.setOnPreparedListener(this);		
					this.mPlayer.prepare();

					// Get duration
					this.duration = getDurationInSeconds();
				}
			} 
			catch (Exception e) { 
				e.printStackTrace(); 
				this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_STARTING_PLAYBACK+");");			
			}
		}

		// If we have already have created an audio player
		else {
			
			// If player has been paused, then resume playback
			if ((this.state == MEDIA_PAUSED) || (this.state == MEDIA_STARTING)) {
				this.mPlayer.start();
				this.setState(MEDIA_RUNNING);
			}
			else {
			    Log.d(LOG_TAG, "AudioPlayer Error: startPlaying() called during invalid state: "+this.state);
				this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_RESUME_STATE+");");			
			}
		}
	} 

	/**
	 * Seek or jump to a new time in the track.
	 */
	public void seekToPlaying(int milliseconds) {
		if (this.mPlayer != null) {
		    this.mPlayer.seekTo(milliseconds);
		    Log.d(LOG_TAG, "Send a onStatus update for the new seek");
		    this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_POSITION+", "+milliseconds/1000.0f+");");
		}
	}
	
	/**
	 * Pause playing.
	 */
	public void pausePlaying() {
		
		// If playing, then pause
		if (this.state == MEDIA_RUNNING) {
			this.mPlayer.pause();
			this.setState(MEDIA_PAUSED);
		}
		else {
		    Log.d(LOG_TAG, "AudioPlayer Error: pausePlaying() called during invalid state: "+this.state);			
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_PAUSE_STATE+");");			
		}
	}

    /**
     * Stop playing the audio file.
     */
	public void stopPlaying() {
		if ((this.state == MEDIA_RUNNING) || (this.state == MEDIA_PAUSED)) {
			this.mPlayer.stop();
			this.setState(MEDIA_STOPPED);
		}
		else {
		    Log.d(LOG_TAG, "AudioPlayer Error: stopPlaying() called during invalid state: "+this.state);			
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+MEDIA_ERROR_STOP_STATE+");");			
		}
	}
	
	/**
	 * Callback to be invoked when playback of a media source has completed.
	 * 
	 * @param mPlayer			The MediaPlayer that reached the end of the file 
	 */
	public void onCompletion(MediaPlayer mPlayer) {
		this.setState(MEDIA_STOPPED);
    } 
	
    /**
     * Get current position of playback.
     * 
     * @return 					position in msec or -1 if not playing
     */
	public long getCurrentPosition() {
		if ((this.state == MEDIA_RUNNING) || (this.state == MEDIA_PAUSED)) {
		    int curPos = this.mPlayer.getCurrentPosition();
		    this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_POSITION+", "+curPos/1000.0f+");");
			return curPos;
		} 
		else { 
			return -1; 
		}
	}
	
	/**
	 * Determine if playback file is streaming or local.
	 * It is streaming if file name starts with "http://"
	 * 
	 * @param file				The file name
	 * @return					T=streaming, F=local
	 */
	public boolean isStreaming(String file) {
		if (file.contains("http://") || file.contains("https://")) {
			return true;
		} 
		else {
			return false;
		}
	}
	
   /**
     * Get the duration of the audio file.
     * 
     * @param file				The name of the audio file.
     * @return					The duration in msec.
     * 							-1=can't be determined
     * 							-2=not allowed
     */
	public float getDuration(String file) {
		
		// Can't get duration of recording
		if (this.recorder != null) {
			return(-2); // not allowed
		}
		
		// If audio file already loaded and started, then return duration
		if (this.mPlayer != null) {
			return this.duration;
		}
		
		// If no player yet, then create one
		else {
			this.prepareOnly = true;
			this.startPlaying(file);
			
			// This will only return value for local, since streaming
			// file hasn't been read yet.
			return this.duration;
		}
	}

	/**
	 * Callback to be invoked when the media source is ready for playback. 
	 * 
	 * @param mPlayer			The MediaPlayer that is ready for playback 
	 */
	public void onPrepared(MediaPlayer mPlayer) {
		// Listen for playback completion
		this.mPlayer.setOnCompletionListener(this);

		// If start playing after prepared
		if (!this.prepareOnly) {
			
			// Start playing
			this.mPlayer.start();

			// Set player init flag
			this.setState(MEDIA_RUNNING);
		}
		
		// Save off duration
		this.duration = getDurationInSeconds();	
		this.prepareOnly = false;

		// Send status notification to JavaScript
		this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_DURATION+","+this.duration+");");
		
	}

	/**
	 * By default Android returns the length of audio in mills but we want seconds
	 * 
	 * @return length of clip in seconds
	 */
	private float getDurationInSeconds() {
		return (this.mPlayer.getDuration() / 1000.0f);
	}

	/**
	 * Callback to be invoked when there has been an error during an asynchronous operation
	 *  (other errors will throw exceptions at method call time).
	 *  
	 * @param mPlayer			the MediaPlayer the error pertains to
	 * @param arg1				the type of error that has occurred: (MEDIA_ERROR_UNKNOWN, MEDIA_ERROR_SERVER_DIED)
	 * @param arg2				an extra code, specific to the error.
	 */
	public boolean onError(MediaPlayer mPlayer, int arg1, int arg2) {
	    Log.d(LOG_TAG, "AudioPlayer.onError(" + arg1 + ", " + arg2+")");

		// TODO: Not sure if this needs to be sent?
		this.mPlayer.stop();
		this.mPlayer.release();
		
		// Send error notification to JavaScript
		this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_ERROR+", "+arg1+");");
		return false;
	}
	
	/**
	 * Set the state and send it to JavaScript.
	 * 
	 * @param state
	 */
	private void setState(int state) {
		if (this.state != state) {
			this.handler.sendJavascript("PhoneGap.Media.onStatus('" + this.id + "', "+MEDIA_STATE+", "+state+");");
		}
		
		this.state = state;
	}

	/**
	 * Set the volume for audio player
	 *
	 * @param volume
	 */
    public void setVolume(float volume) {
        this.mPlayer.setVolume(volume, volume);
    }
}
