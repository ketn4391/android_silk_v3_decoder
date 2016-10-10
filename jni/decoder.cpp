//
// Created by ketian on 16-9-23.
//

#include <jni.h>

#ifdef __cplusplus
extern "C"
{
#endif

#include "silk.h"
#include "lame.h"

JNICALL jint xx(JNIEnv *env, jclass clazz, jstring src, jstring dest) {
    const char *str_c = env->GetStringUTFChars(src, 0);
    const char *dest_c = env->GetStringUTFChars(dest, 0);

    const char *tmp = "/data/data/com.ketian.android.silkv3/t.t";

    if (x(str_c, tmp) == 0) {
        lame_t lame = lame_init();
        lame_set_in_samplerate(lame, 24000);


        lame_set_num_channels(lame, 1);
        lame_set_mode(lame, MONO);
        lame_set_quality(lame, 5);
        lame_init_params(lame);

        FILE *pcm = fopen(tmp, "rb");
        FILE *mp3 = fopen(dest_c, "wb");
        int read, write;

        const int PCM_SIZE = 8192;
        const int MP3_SIZE = 8192;
        short int pcm_buffer[PCM_SIZE];
        unsigned char mp3_buffer[MP3_SIZE];

        do {
            read = fread(pcm_buffer, sizeof(short int), PCM_SIZE, pcm);
            if (read == 0) {
                write = lame_encode_flush(lame, mp3_buffer, MP3_SIZE);
            } else {
                write = lame_encode_buffer(lame, pcm_buffer, NULL, read, mp3_buffer, MP3_SIZE);
            }

            fwrite(mp3_buffer, 1, write, mp3);
        } while (read != 0);

        lame_close(lame);
        fclose(mp3);
        fclose(pcm);
        return 0;
    }

    return -1;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    JNINativeMethod gMethod[] = {
            {"x", "(Ljava/lang/String;Ljava/lang/String;)I", (void *) xx},
    };

    jclass clazz = env->FindClass("com/ketian/android/silkv3/jni/JNI");
    if (clazz == NULL) {
        return JNI_ERR;
    }

    if (env->RegisterNatives(clazz, gMethod, 1) < 0) {
        return false;
    }

    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved) {
    return;
}

#ifdef __cplusplus
}
#endif