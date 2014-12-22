LOCAL_PATH :=$(call my-dir)

include ${CLEAR_VARS}

LOCAL_MODULE  := fileutil
LOCAL_SRC_FILES := fileutil.c


include ${BUILD_SHARED_LIBRARY}
