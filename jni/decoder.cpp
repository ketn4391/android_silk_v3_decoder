//
// Created by ketian on 16-9-23.
//

#include <jni.h>

#ifdef __cplusplus
extern "C"
{
#endif

#include <android/log.h>
#include "silk.h"
#include "lame.h"

JNIEXPORT jint JNICALL
Java_com_kgo_silk_JNI_convert(JNIEnv *env, jclass clazz, jstring src, jstring dest, jstring tmpfile) {
    const char *str_c = env->GetStringUTFChars(src, 0);
    const char *dest_c = env->GetStringUTFChars(dest, 0);

    const char *tmp = env->GetStringUTFChars(tmpfile, 0);

    __android_log_print(ANDROID_LOG_DEBUG, "tian.ke",
                        "libsilkx is developed by tian.ke, any question, please email to ketn4391@gmail.com");

    __android_log_print(ANDROID_LOG_DEBUG, "tian.ke", "convert %s to %s", str_c, dest_c);
    __android_log_print(ANDROID_LOG_DEBUG, "tian.ke", "tmpfile = %s", tmp);

    if (convertSilk2PCM(str_c, tmp) == 0) {
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

#ifdef __cplusplus
}
#endif