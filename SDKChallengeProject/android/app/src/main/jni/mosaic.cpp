//马赛克算法的实现
// Created by bear on 2020/4/15.
//


#include <jni.h>
#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <android/log.h>

extern "C" {


unsigned char ClipValue(unsigned char x, unsigned char min_val, unsigned char max_val);

int convertColor(int color, int size);

/**
 * 输入为YUV格式
 * input_yuv:YUV数据
 * width: 宽度
 * height: 高度
 * scale: 像素块大小
 */
void
pixStyleYuv(unsigned char *input_yuv, unsigned char *output_yuv, int width, int height, int scale,
            int bit);


JNIEXPORT jbyteArray JNICALL Java_cn_bearever_likemosaic_home_MosaicVideoSink_mosaicI420(
        JNIEnv *env, jclass thiz, jbyteArray data,
        jint width, jint height, jint scale, jint bit) {
    int len = env->GetArrayLength(data);
    u_char *out = (u_char *) malloc(len);
    jboolean copy;
    u_char *input = reinterpret_cast<u_char *>(env->GetByteArrayElements(data, &copy));
    pixStyleYuv(input, out, width, height, scale, bit);
    jbyteArray outData = env->NewByteArray(len);
    env->SetByteArrayRegion(outData, 0, len, (jbyte *) out);
    return outData;
}


int convertColor(int color, int size) {
    int step = 256 / size;
    color = 256 - color;
    color = 256 - color / step * step;
    if (color > 255) {
        return 255;
    }
    if (color < 0) {
        return 0;
    }
    return color;
}

/**
 * 输入为YUV格式
 * input_yuv:YUV数据
 * width: 宽度
 * height: 高度
 * scale: 像素块大小
 */
void
pixStyleYuv(unsigned char *input_yuv, unsigned char *output_yuv, int width, int height, int scale,
            int bit) {
    int len = width * height;
    unsigned char *out_y = (unsigned char *) malloc(len);
    unsigned char *out_u = (unsigned char *) malloc(len / 4);
    unsigned char *out_v = (unsigned char *) malloc(len / 4);
    //像素化

    memcpy(out_y, input_yuv, len);
    memcpy(out_u, input_yuv + len, len / 4);
    memcpy(out_v, input_yuv + len + len / 4, len / 4);
    //处理Y分量，分割为8个值
    int index, tindex, y;

    for (int i = 0; i < height; i += scale) {
        for (int j = 0; j < width; j += scale) {
            index = width * i + j;
            y = out_y[index];

            for (int k = 0; k < scale; k++) {
                for (int p = 0; p < scale; p++) {
                    tindex = index + (k * width + p);
                    if (tindex < len) {
                        y += out_y[tindex];
                    }
                }
            }
            y = y / scale / scale;
            for (int k = 0; k < scale; k++) {
                for (int p = 0; p < scale; p++) {
                    tindex = index + (k * width + p);
                    if (tindex < len) {
                        out_y[tindex] = convertColor(y, bit);
                    }
                }
            }
        }
    }

    //处理UV分量
    int u, v;
    index = tindex = u = v = 0;
    scale = scale / 2;
    for (int i = 0; i < height / 2; i += scale) {
        for (int j = 0; j < width / 2; j += scale) {
            index = width / 2 * i + j;
            u = v = 0;
            for (int k = 0; k < scale; k++) {
                for (int p = 0; p < scale; p++) {
                    tindex = index + (k * width / 2 + p);
                    if (tindex < len / 4) {
                        u = u + out_u[tindex];
                        v = v + out_v[tindex];
                    }
                }
            }
            u = u / scale / scale;
            v = v / scale / scale;

            for (int k = 0; k < scale; k++) {
                for (int p = 0; p < scale; p++) {
                    tindex = index + (k * width / 2 + p);
                    if (tindex < len / 4) {
                        out_u[tindex] = u;
                        out_v[tindex] = v;
                    }
                }
            }
        }
    }

    //将yuv三个分量合到一个char*
    memcpy(output_yuv, out_y, len);
    memcpy(output_yuv + len, out_u, len / 4);
    memcpy(output_yuv + len + len / 4, out_v, len / 4);
    free(out_y);
    free(out_u);
    free(out_v);
    printf("pix style yuv end\n");
}
unsigned char ClipValue(unsigned char x, unsigned char min_val, unsigned char max_val) {
    if (x > max_val) {
        return max_val;
    } else if (x < min_val) {
        return min_val;
    } else {
        return x;
    }
}
unsigned char *rgb2yuv420(unsigned char *rgb24, int width, int height) {
    unsigned char *ptrY, *ptrU, *ptrV;
    unsigned char *yuv420p = (unsigned char *) malloc(width * height * 3 / 2);
    memset(yuv420p, 0, width * height * 3 / 2);
    ptrY = yuv420p;
    ptrU = yuv420p + width * height;
    ptrV = ptrU + (width * height * 1 / 4);
    unsigned char y, u, v, r, g, b;
    int index = 0;
    for (int j = 0; j < height; j++) {
        for (int i = 0; i < width; i++) {
            index = width * j * 3 + i * 3;
            r = rgb24[index];
            g = rgb24[index + 1];
            b = rgb24[index + 2];
            y = (unsigned char) ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
            u = (unsigned char) ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
            v = (unsigned char) ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;
            *(ptrY++) = ClipValue(y, 0, 255);
            if (j % 2 == 0 && i % 2 == 0) {
                *(ptrU++) = ClipValue(u, 0, 255);
            } else if (i % 2 == 0) {
                *(ptrV++) = ClipValue(v, 0, 255);
            }
        }
    }
    return yuv420p;
}


}
