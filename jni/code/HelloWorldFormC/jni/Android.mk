   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)
	# 对应打包成函数库的名字
   LOCAL_MODULE    := hello
   # 对应c代码的文件
   LOCAL_SRC_FILES := Hello.c

   include $(BUILD_SHARED_LIBRARY)