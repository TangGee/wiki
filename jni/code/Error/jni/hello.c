#include<stdio.h>
#include <string.h>
#include "com_example_error_DemoActivity.h"


JNIEXPORT jstring JNICALL Java_com_example_error_DemoActivity_helloWorld
  (JNIEnv * env, jobject jobje){
	//const  final
	//
	const char* c="hello";
	strcat(c,"nihao");

}
