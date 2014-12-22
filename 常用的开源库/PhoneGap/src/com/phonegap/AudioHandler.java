/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap;

import android.content.Context;
import android.media.AudioManager;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This class called by PhonegapActivity to play and record audio.  
 * The file can be local or over a network using http.
 * 
 * Audio formats supported (tested):
 * 	.mp3, .wav
 * 
 * Local audio files must reside in one of two places:
 * 		android_asset: 		file name must start with /android_asset/sound.mp3
 * 		sdcard:				file name is just sound.mp3
 */
public class AudioHandler extends Plugin {

	HashMap<String,AudioPlayer> players;	// Audio player object
	
	/**
	 * Constructor.
	 */
	public AudioHandler() {
		this.players = new HashMap<String,AudioPlayer>();
	}

	/**
	 * Executes the request and returns PluginResult.
	 * 
	 * @param action 		The action to execute.
	 * @param args 			JSONArry of arguments for the plugin.
	 * @param callbackId	The callback id used when calling back into JavaScript.
	 * @return 				A PluginResult object with a status and message.
	 */
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		PluginResult.Status status = PluginResult.Status.OK;
		String result = "";		
		
		try {
			if (action.equals("startRecordingAudio")) {
				this.startRecordingAudio(args.getString(0), args.getString(1));
			}
			else if (action.equals("stopRecordingAudio")) {
				this.stopRecordingAudio(args.getString(0));
			}
			else if (action.equals("startPlayingAudio")) {
				this.startPlayingAudio(args.getString(0), args.getString(1));
			}
			else if (action.equals("seekToAudio")) {
				this.seekToAudio(args.getString(0), args.getInt(1));
			}
			else if (action.equals("pausePlayingAudio")) {
				this.pausePlayingAudio(args.getString(0));
			}
			else if (action.equals("stopPlayingAudio")) {
				this.stopPlayingAudio(args.getString(0));
			} else if (action.equals("setVolume")) {
			   try {
				   this.setVolume(args.getString(0), Float.parseFloat(args.getString(1)));
			   } catch (NumberFormatException nfe) {
				   //no-op
			   }
			} else if (action.equals("getCurrentPositionAudio")) {
				float f = this.getCurrentPositionAudio(args.getString(0));
				return new PluginResult(status, f);
			}
			else if (action.equals("getDurationAudio")) {
				float f = this.getDurationAudio(args.getString(0), args.getString(1));
				return new PluginResult(status, f);
			}
			else if (action.equals("release")) {
				boolean b = this.release(args.getString(0));
				return new PluginResult(status, b);
			}
			return new PluginResult(status, result);
		} catch (JSONException e) {
			e.printStackTrace();
			return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}

	/**
	 * Identifies if action to be executed returns a value and should be run synchronously.
	 * 
	 * @param action	The action to execute
	 * @return			T=returns value
	 */
	public boolean isSynch(String action) {
		if (action.equals("getCurrentPositionAudio")) {
			return true;
		}
		else if (action.equals("getDurationAudio")) {
			return true;
		}
		return false;
	}

	/**
	 * Stop all audio players and recorders.
	 */
	public void onDestroy() {
		java.util.Set<Entry<String,AudioPlayer>> s = this.players.entrySet();
        java.util.Iterator<Entry<String,AudioPlayer>> it = s.iterator();
        while(it.hasNext()) {
            Entry<String,AudioPlayer> entry = it.next();
            AudioPlayer audio = entry.getValue();
            audio.destroy();
		}
        this.players.clear();
	}

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------
	
	/**
	 * Release the audio player instance to save memory.
	 * 
	 * @param id				The id of the audio player
	 */
	private boolean release(String id) {
    	if (!this.players.containsKey(id)) {
    		return false;
    	}
    	AudioPlayer audio = this.players.get(id);
    	this.players.remove(id);
    	audio.destroy();
    	return true;
	}

	/**
	 * Start recording and save the specified file.
	 * 
	 * @param id				The id of the audio player
	 * @param file				The name of the file
	 */
    public void startRecordingAudio(String id, String file) {
    	// If already recording, then just return;
    	if (this.players.containsKey(id)) {
    		return;
    	}
    	AudioPlayer audio = new AudioPlayer(this, id);
    	this.players.put(id, audio);
    	audio.startRecording(file);
    }

    /**
     * Stop recording and save to the file specified when recording started.
     * 
	 * @param id				The id of the audio player
     */
    public void stopRecordingAudio(String id) {
    	AudioPlayer audio = this.players.get(id);
    	if (audio != null) {
    		audio.stopRecording();
    		this.players.remove(id);
    	}
    }
    
    /**
     * Start or resume playing audio file.
     * 
	 * @param id				The id of the audio player
     * @param file				The name of the audio file.
     */
    public void startPlayingAudio(String id, String file) {
    	AudioPlayer audio = this.players.get(id);
    	if (audio == null) {
    		audio = new AudioPlayer(this, id);
    		this.players.put(id, audio);
    	}
    	audio.startPlaying(file);
    }

    /**
     * Seek to a location.
     * 
     * 
	 * @param id				The id of the audio player
	 * @param miliseconds		int: number of milliseconds to skip 1000 = 1 second
     */
    public void seekToAudio(String id, int milliseconds) {
    	AudioPlayer audio = this.players.get(id);
    	if (audio != null) {
    		audio.seekToPlaying(milliseconds);
    	}
    }
    
    /**
     * Pause playing.
     * 
	 * @param id				The id of the audio player
     */
    public void pausePlayingAudio(String id) {
    	AudioPlayer audio = this.players.get(id);
    	if (audio != null) {
    		audio.pausePlaying();
    	}
    }

    /**
     * Stop playing the audio file.
     * 
	 * @param id				The id of the audio player
     */
    public void stopPlayingAudio(String id) {
    	AudioPlayer audio = this.players.get(id);
    	if (audio != null) {
    		audio.stopPlaying();
    		//audio.destroy();
    		//this.players.remove(id);
    	}
    }
    
    /**
     * Get current position of playback.
     * 
	 * @param id				The id of the audio player
     * @return 					position in msec
     */
    public float getCurrentPositionAudio(String id) {
    	AudioPlayer audio = this.players.get(id);
    	if (audio != null) {
    		return(audio.getCurrentPosition()/1000.0f);
    	}
    	return -1;
    }
    
    /**
     * Get the duration of the audio file.
     * 
	 * @param id				The id of the audio player
     * @param file				The name of the audio file.
     * @return					The duration in msec.
     */
    public float getDurationAudio(String id, String file) {
    	
    	// Get audio file
    	AudioPlayer audio = this.players.get(id);
    	if (audio != null) {
    		return(audio.getDuration(file));
    	}
    	
    	// If not already open, then open the file
    	else {
    		audio = new AudioPlayer(this, id);
    		this.players.put(id, audio);
    		return(audio.getDuration(file));
    	}
    }  
    
    /**
     * Set the audio device to be used for playback.
     * 
     * @param output			1=earpiece, 2=speaker
     */
    public void setAudioOutputDevice(int output) {
		AudioManager audiMgr = (AudioManager) this.ctx.getSystemService(Context.AUDIO_SERVICE);
		if (output == 2) {
			audiMgr.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_SPEAKER, AudioManager.ROUTE_ALL);
		}
		else if (output == 1) {
			audiMgr.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
		}
		else {
			System.out.println("AudioHandler.setAudioOutputDevice() Error: Unknown output device.");
		}
    }
    
    /**
     * Get the audio device to be used for playback.
     * 
     * @return					1=earpiece, 2=speaker
     */
    public int getAudioOutputDevice() {
		AudioManager audiMgr = (AudioManager) this.ctx.getSystemService(Context.AUDIO_SERVICE);
		if (audiMgr.getRouting(AudioManager.MODE_NORMAL) == AudioManager.ROUTE_EARPIECE) {
			return 1;
		}
		else if (audiMgr.getRouting(AudioManager.MODE_NORMAL) == AudioManager.ROUTE_SPEAKER) {
			return 2;
		}
		else {
			return -1;
		}
    }

    /**
     * Set the volume for an audio device
     *
     * @param id				The id of the audio player
     * @param volume            Volume to adjust to 0.0f - 1.0f
     */
    public void setVolume(String id, float volume) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.setVolume(volume);
        } else {
            System.out.println("AudioHandler.setVolume() Error: Unknown Audio Player " + id);
        }
    }
}
