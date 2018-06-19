//
// Created by ketian on 16-9-23.
//

#ifndef JNI_SILK_H
#define JNI_SILK_H

#include <android/log.h>
#include <stdio.h>

int convertSilk2PCM(const char *src, const FILE *dest);

#define LOG_TAG "tian.ke"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

#endif //JNI_SILK_H
