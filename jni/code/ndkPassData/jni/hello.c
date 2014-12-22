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
	// ����logcat����̨�� ��ӡ��־
	LOGD("x=%d",x);
	LOGI("y=%d",y);
	// log.i(TAG,"sss");
	return x+y;

}


JNIEXPORT jstring JNICALL Java_com_example_ndkpassdata_DataProvider_sayHelloInC
  (JNIEnv * env, jobject jobject, jstring str){

	char* c="hello";
	// ��C�����в���ֱ�Ӳ���java�е��ַ���
	// ��java�е��ַ���ת����c������ char����
	char* cstr=Jstring2CStr(env,str);

	strcat(cstr,c);
	LOGD("%s",cstr);
	return  (*env)->NewStringUTF(env,cstr);
}

JNIEXPORT jintArray JNICALL Java_com_example_ndkpassdata_DataProvider_intMethod
  (JNIEnv * env, jobject jobject, jintArray jarray){
	// jArray  ��������   jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
	// ����ĳ���    jsize       (*GetArrayLength)(JNIEnv*, jarray);
	// ��������ÿ��Ԫ�� +5
	int length=(*env)->GetArrayLength(env,jarray);
	int* array=(*env)->GetIntArrayElements(env,jarray,0);
	int i=0;
	for(;i<length;i++){
		*(array+i)+=5;
	}
	return jarray;
}
