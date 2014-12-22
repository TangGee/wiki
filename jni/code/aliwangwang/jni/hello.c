#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "com_example_aliwangwang_MainActivity.h"

int login(char* psw){
     char* rightword="123";
     int i=strcmp(rightword,psw);
      if(i==0){
               return 200;
      } else{
             return 302;
      }

}
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

JNIEXPORT jint JNICALL Java_com_example_aliwangwang_MainActivity_login
  (JNIEnv * env, jobject obj, jstring str){
	//  ×ª»»
	char* c=Jstring2CStr(env,str);
	return login(c);
}
