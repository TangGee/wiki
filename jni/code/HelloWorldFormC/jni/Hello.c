#include <stdio.h>
#include "com_example_helloworldformc_MainActivity.h";  // 引入ndk环境里面的头文件 需要用<> 引用代码中的头文件 ""

//public native String helloWorldFromC(){}
jstring Java_com_example_helloworldformc_MainActivity_helloWorldFromC(JNIEnv* env,jobject obj){
	// 2 步 实现C代码
		// 返回一个java String 类型的字符串
	//jstring     (*NewStringUTF)(JNIEnv*, const char*);
	//(*env) 相当于 JNINativeInterface* JNIEnv
	//*(*env)  相当于 JNINativeInterface
	///return (**env).NewStringUTF(env,"helloworldfromc");

	return  (*env)->NewStringUTF(env,"helloworldfromc");


	// andrond.mk   告诉编译器 如何把c代码打包成函数库
	// 3 生成 .mk文件
	// 4步 把c代码 打包成函数库
}

JNIEXPORT jstring JNICALL Java_com_example_helloworldformc_MainActivity_hello_1world_1from_1c
  (JNIEnv * env, jobject obj){



}

}
