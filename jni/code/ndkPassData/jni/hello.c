#include <stdio.h>
#include "com_example_ndkpassdata_DataProvider.h"
#include <android/log.h>
#include <string.h>
#define LOG_TAG "clog"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

char*   Jstring2CStr(JNIEnv*   env,   jstring   jstr)
{
	 char*   rtn   =   NULL;
	 jclass   clsstring   =   (*env)->FindClass(env,"java/lang/String");
	 jstring   strencode   =   (*env)->NewStringUTF(env,"GB2312");
	 jmethodID   mid   =   (*env)->GetMethodID(env,clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
	 jbyteArray   barr=   (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode); // String .getByte("GB2312");
	 jsize   alen   =   (*env)->GetArrayLength(env,barr);
	 jbyte*   ba   =   (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
	 if(alen   >   0)
	 {
	  rtn   =   (char*)malloc(alen+1);         //"\0"
	  memcpy(rtn,ba,alen);
	  rtn[alen]=0;
	 }
	 (*env)->ReleaseByteArrayElements(env,barr,ba,0);  //
	 return rtn;
}


JNIEXPORT jint JNICALL Java_com_example_ndkpassdata_DataProvider_add
  (JNIEnv * env, jobject jobject, jint x, jint y){
	// 想在logcat控制台上 打印日志
	LOGD("x=%d",x);
	LOGI("y=%d",y);
	// log.i(TAG,"sss");
	return x+y;

}


JNIEXPORT jstring JNICALL Java_com_example_ndkpassdata_DataProvider_sayHelloInC
  (JNIEnv * env, jobject jobject, jstring str){

	char* c="hello";
	// 在C语言中不能直接操作java中的字符串
	// 把java中的字符串转换成c语言中 char数组
	char* cstr=Jstring2CStr(env,str);

	strcat(cstr,c);
	LOGD("%s",cstr);
	return  (*env)->NewStringUTF(env,cstr);
}

JNIEXPORT jintArray JNICALL Java_com_example_ndkpassdata_DataProvider_intMethod
  (JNIEnv * env, jobject jobject, jintArray jarray){
	// jArray  遍历数组   jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
	// 数组的长度    jsize       (*GetArrayLength)(JNIEnv*, jarray);
	// 对数组中每个元素 +5
	int length=(*env)->GetArrayLength(env,jarray);
	int* array=(*env)->GetIntArrayElements(env,jarray,0);
	int i=0;
	for(;i<length;i++){
		*(array+i)+=5;
	}
	return jarray;
}
