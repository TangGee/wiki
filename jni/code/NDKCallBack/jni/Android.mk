 LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)

   LOCAL_MODULE    := libhello
   LOCAL_SRC_FILES := Hello.c

   include $(BUILD_SHARED_LIBRARY)