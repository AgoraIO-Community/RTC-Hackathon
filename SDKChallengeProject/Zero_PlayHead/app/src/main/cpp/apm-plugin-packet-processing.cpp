#include <jni.h>
#include <android/log.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <assert.h>

#include <vector>
#include <algorithm>

#include "./include/agora/IAgoraMediaEngine.h"
#include "./include/agora/IAgoraRtcEngine.h"

#include "apm-plugin-packet-processing.h"

class AgoraVideoFrameObserver : public agora::media::IVideoFrameObserver {

public:
    AgoraVideoFrameObserver() {

    }

    AgoraVideoFrameObserver(JavaVM *vm, JNIEnv *env, jobject jobj) {
        this->vm = vm;
        this->jobj = jobj;
        jclass jclaz = env->GetObjectClass(jobj);
        jSendMethodId = env->GetMethodID(jclaz, "onSend", "([BIII)V");
        jQueryMethodId = env->GetMethodID(jclaz, "isHandling", "()Z");
    }


    // 获取本地摄像头采集到的视频帧
    virtual bool onCaptureVideoFrame(VideoFrame &videoFrame) override {
        if (!isInit) {
            vm->AttachCurrentThread(&env, 0);
            isInit = true;
        }
        if (env->CallBooleanMethod(jobj, jQueryMethodId)) {
            return true;
        }
        int width = videoFrame.width;
        int height = videoFrame.height;
        int index = 0;
        char *rgba = new char[width * height * 4];
        unsigned char *ybase = static_cast<unsigned char *>(videoFrame.yBuffer);
        unsigned char *ubase = static_cast<unsigned char *>(videoFrame.uBuffer);;
        unsigned char *vbase = static_cast<unsigned char *>(videoFrame.vBuffer);;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //YYYYYYYYUUVV
                u_char Y = ybase[x + y * width];
                u_char U = ubase[y / 2 * width / 2 + (x / 2)];
                u_char V = vbase[y / 2 * width / 2 + (x / 2)];
                int r = static_cast<int>(Y + 1.402 * (V - 128));
                if (r > 255) {
                    r = 255;
                }
                if (r < 0) {
                    r = 0;
                }
                int g = static_cast<int>(Y - 0.34413 * (U - 128) -
                                         0.71414 * (V - 128));
                if (g > 255) {
                    g = 255;
                }
                if (g < 0) {
                    g = 0;
                }

                int b = static_cast<int>(Y + 1.772 * (U - 128));
                if (b > 255) {
                    b = 255;
                }
                if (b < 0) {
                    b = 0;
                }

                rgba[index++] = static_cast<char>(r); //R
                rgba[index++] = static_cast<char>(g); //G
                rgba[index++] = static_cast<char>(b); //B
                rgba[index++] = static_cast<char>(255);
            }
        }

        jbyte buf[width * height * 4];
        int i = 0;
        for (i = 0; i < width * height * 4; i++) {
            buf[i] = rgba[i];
        }


        jbyteArray jarrRV = env->NewByteArray(width * height * 4);
        env->SetByteArrayRegion(jarrRV, 0, width * height * 4, buf);
        env->CallVoidMethod(jobj, jSendMethodId, jarrRV, width, height, videoFrame.rotation);
        env->DeleteLocalRef(jarrRV);

        return true;
    }

    // 获取远端用户发送的视频帧
    virtual bool onRenderVideoFrame(unsigned int uid, VideoFrame &videoFrame) override {
        __android_log_print(ANDROID_LOG_INFO, "ldh", "onRenderVideoFrame");
        return true;
    }

    // 获取本地视频编码前的视频帧
    virtual bool onPreEncodeVideoFrame(VideoFrame &videoFrame) override {
        __android_log_print(ANDROID_LOG_INFO, "ldh", "onPreEncodeVideoFrame");
        return true;
    }

    void release() {
        vm->DetachCurrentThread();
    }

private:
    JavaVM *vm;
    jobject jobj;
    jmethodID jSendMethodId;
    jmethodID jQueryMethodId;
    JNIEnv *env;
    bool isInit = false;
};

static AgoraVideoFrameObserver s_packetObserver;

static agora::rtc::IRtcEngine *rtcEngine = NULL;

JavaVM *jvm;

#ifdef __cplusplus
extern "C" {
#endif

int __attribute__((visibility("default")))
loadAgoraRtcEnginePlugin(agora::rtc::IRtcEngine *engine) {
    rtcEngine = engine;
    __android_log_print(ANDROID_LOG_INFO, "ldh", "load");
    return 0;
}

void __attribute__((visibility("default")))
unloadAgoraRtcEnginePlugin(agora::rtc::IRtcEngine *engine) {
    rtcEngine = NULL;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;
    jvm = vm;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }

    assert(env != NULL);
    result = JNI_VERSION_1_6;
    return result;
}

JNIEXPORT void JNICALL Java_com_zero_game_utils_frame_VideoFrameHandler_doRegisterProcessing
        (JNIEnv *env, jobject obj) {
    if (!rtcEngine) {
        __android_log_print(ANDROID_LOG_INFO, "ldh", "register null");
        return;
    } else {
        __android_log_print(ANDROID_LOG_INFO, "ldh", "register");
        agora::util::AutoPtr<agora::media::IMediaEngine> mediaEngine;
        mediaEngine.queryInterface(rtcEngine, agora::AGORA_IID_MEDIA_ENGINE);
        s_packetObserver = *new AgoraVideoFrameObserver(jvm, env, env->NewGlobalRef(obj));
        mediaEngine->registerVideoFrameObserver(&s_packetObserver);
    }
}

JNIEXPORT void JNICALL Java_com_zero_game_utils_frame_VideoFrameHandler_doUnregisterProcessing
        (JNIEnv *env, jobject obj) {
    if (!rtcEngine) {
        __android_log_print(ANDROID_LOG_INFO, "ldh", "unregister null");
        return;
    } else {
        __android_log_print(ANDROID_LOG_INFO, "ldh", "unregister");
        agora::util::AutoPtr<agora::media::IMediaEngine> mediaEngine;
        mediaEngine.queryInterface(rtcEngine, agora::AGORA_IID_MEDIA_ENGINE);
        s_packetObserver.release();
        mediaEngine->registerVideoFrameObserver(nullptr);
    }
}

#ifdef __cplusplus
}
#endif