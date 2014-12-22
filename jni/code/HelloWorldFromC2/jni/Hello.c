#include <stdio.h>
#include "com_example_helloworldfromc2_MainActivity.h"




JNIEXPORT jstring JNICALL Java_com_example_helloworldfromc2_MainActivity_h_1e_11_1llo
  (JNIEnv * env, jobject obj){

	// char* "hellofromc"
	return (*env)->NewStringUTF(env,"hellofromc");
	//return "hellofromc";

}
