#include "com_example_ndkcallback_DataProvider.h"


JNIEXPORT void JNICALL Java_com_example_ndkcallback_DataProvider_callMethod1
  (JNIEnv * env, jobject jobject){

	/*
	 *
		Class<?> forName = Class.forName("com.example.ndkcallback.DataProvider");
		Method declaredMethod = forName.getDeclaredMethod("helloFromJava", new Class[]{});
		declaredMethod.invoke(forName.newInstance(), new Object[]{});
	 *
	 *
	 */
	///jclass      (*FindClass)(JNIEnv*, const char*);
	jclass clazz=(*env)->FindClass(env,"com/example/ndkcallback/DataProvider");
	//  jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
	// ����ǩ��  �����ͷ���ֵ
	jmethodID methodId=(*env)->GetMethodID(env,clazz,"helloFromJava","()V");
	// void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
	(*env)->CallVoidMethod(env,jobject,methodId);
}

JNIEXPORT void JNICALL Java_com_example_ndkcallback_DataProvider_callMethod2
  (JNIEnv *  env, jobject jobject){
	jclass clazz=(*env)->FindClass(env,"com/example/ndkcallback/DataProvider");
	jmethodID methodId=(*env)->GetMethodID(env,clazz,"Add","(II)I");
	// jint        (*CallIntMethod)(JNIEnv*, jobject, jmethodID, ...);
	(*env)->CallIntMethod(env,jobject,methodId,3,5);
}

JNIEXPORT void JNICALL Java_com_example_ndkcallback_DataProvider_callMethod3
  (JNIEnv * env, jobject jobject){   // ���� object  ����native�������ڵ���
	jclass clazz=(*env)->FindClass(env,"com/example/ndkcallback/DataProvider");
		jmethodID methodId=(*env)->GetMethodID(env,clazz,"printString","(Ljava/lang/String;)V");
		// jint        (*CallIntMethod)(JNIEnv*, jobject, jmethodID, ...);
		jstring str=(*env)->NewStringUTF(env,"hello");

		(*env)->CallVoidMethod(env,jobject,methodId,str);

}

JNIEXPORT void JNICALL Java_com_example_ndkcallback_DataProvider_callMethod4
  (JNIEnv * env, jobject j){
	jclass clazz=(*env)->FindClass(env,"com/example/ndkcallback/MainActivity");
	//  jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
	// ����ǩ��  �����ͷ���ֵ
	jmethodID methodId=(*env)->GetMethodID(env,clazz,"helloFromJava","()V");
	// void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
	// ��Ҫ����DataProvider�� ����
	// jobject     (*AllocObject)(JNIEnv*, jclass);
	jobject obj=(*env)->AllocObject(env,clazz);  // new MainActivity();
	(*env)->CallVoidMethod(env,obj,methodId);

}

JNIEXPORT void JNICALL Java_com_example_ndkcallback_DataProvider_callMethod5
  (JNIEnv * env, jobject j){
	jclass clazz=(*env)->FindClass(env,"com/example/ndkcallback/DataProvider");
	//     jmethodID   (*GetStaticMethodID)(JNIEnv*, jclass, const char*, const char*);
	 jmethodID  methodid=(*env)->GetStaticMethodID(env,clazz,"demo","()V");
	//void        (*CallStaticVoidMethod)(JNIEnv*, jclass, jmethodID, ...);
	 (*env)->CallStaticVoidMethod(env,clazz,methodid);
}
