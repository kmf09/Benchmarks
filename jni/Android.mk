LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

SRC := $(wildcard $(LOCAL_PATH)/*.c)

LOCAL_MODULE    := native_bench
LOCAL_SRC_FILES := $(SRC:$(LOCAL_PATH)/%=%)
LOCAL_LDLIBS    += -llog 

include $(BUILD_SHARED_LIBRARY)
