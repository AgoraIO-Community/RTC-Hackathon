#ifndef ADVANCED_VIDEO_ANDROID_PACKET_PROCESSING_PLUGIN_JNI_H
#define ADVANCED_VIDEO_ANDROID_PACKET_PROCESSING_PLUGIN_JNI_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_zero_game_frame_VideoFrameHandler_doRegisterProcessing(JNIEnv *, jobject);

JNIEXPORT void JNICALL Java_com_zero_game_frame_VideoFrameHandler_doUnregisterProcessing(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif

#endif //ADVANCED_VIDEO_ANDROID_PACKET_PROCESSING_PLUGIN_JNI_H