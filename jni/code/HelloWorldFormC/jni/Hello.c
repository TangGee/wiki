#include <stdio.h>
#include "com_example_helloworldformc_MainActivity.h";  // ����ndk���������ͷ�ļ� ��Ҫ��<> ���ô����е�ͷ�ļ� ""

//public native String helloWorldFromC(){}
jstring Java_com_example_helloworldformc_MainActivity_helloWorldFromC(JNIEnv* env,jobject obj){
	// 2 �� ʵ��C����
		// ����һ��java String ���͵��ַ���
	//jstring     (*NewStringUTF)(JNIEnv*, const char*);
	//(*env) �൱�� JNINativeInterface* JNIEnv
	//*(*env)  �൱�� JNINativeInterface
	///return (**env).NewStringUTF(env,"helloworldfromc");

	return  (*env)->NewStringUTF(env,"helloworldfromc");


	// andrond.mk   ���߱����� ��ΰ�c�������ɺ�����
	// 3 ���� .mk�ļ�
	// 4�� ��c���� ����ɺ�����
}

JNIEXPORT jstring JNICALL Java_com_example_helloworldformc_MainActivity_hello_1world_1from_1c
  (JNIEnv * env, jobject obj){



}

}
