#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <utime.h>
#include <time.h>
#include <jni.h>

char* js2c(JNIEnv* env, jstring jstr);
/**
 * 用于返回文件的最近访问时间
 */
JNIEXPORT jlong JNICALL Java_com_example_redbaby_utils_FileUtil_getFileAccessTime
  (JNIEnv * env, jclass jclazz, jstring path)
{
	char *filepath;
	struct stat statbuf;

	filepath = js2c(env, path);
	if (stat(filepath, &statbuf) < 0)
		return -1;
	return statbuf.st_atime;

}

JNIEXPORT jlong JNICALL Java_com_example_redbaby_utils_FileUtil_howmanyTimeUnAccess(
		JNIEnv * env, jclass jclazz, jstring path) {
	time_t rettime;
	char *filepath;
	struct stat statbuf;
	filepath = js2c(env, path);

	if ((rettime = time(NULL) )== -1)
		return -1;

	if (stat(filepath, &statbuf) < 0)
		return -1;

//	return 0;
	return (rettime-statbuf.st_atime);

}

JNIEXPORT jlong JNICALL Java_com_example_redbaby_utils_FileUtil_data(JNIEnv * env,
		jclass jclazz)
{
	time_t rettime;

	if ((rettime = time(NULL) )== -1)
		return -1;
	return rettime;

}

char* js2c(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "utf-8");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
			strencode);
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	return rtn;
}
