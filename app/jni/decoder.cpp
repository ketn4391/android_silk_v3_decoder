//
// Created by ketian on 16-9-23.
//

#include <jni.h>
#include <unistd.h>

#ifdef __cplusplus
extern "C"
{
#endif

#include "silk.h"
#include "lame.h"

JNIEXPORT jint JNICALL
Java_com_ketian_android_silkv3_jni_JNI_convert(JNIEnv *env, jobject thiz, jstring src, jstring dest, jstring tmpfile) {
    if (src == nullptr){
        return NULL;
    }
    if (dest == nullptr){
        return NULL;
    }
    if (tmpfile == nullptr){
        return NULL;
    }
    const char *str_c = env->GetStringUTFChars(src, nullptr);
    const char *dest_c = env->GetStringUTFChars(dest, nullptr);
    const char *tmp = env->GetStringUTFChars(tmpfile, nullptr);
    LOGE("libsilkx is developed by tian.ke, any question, please email to ketn4391@gmail.com");
    LOGE("convert %s to %s", str_c, dest_c);
    FILE *tempFile = fopen(tmp, "wb+e");
    if (tempFile == nullptr) {
        LOGE("open tempFile %s failed", tmp);
        return -1;
    }
    if (convertSilk2PCM(str_c, tempFile) != 0) {
        LOGE("convert silk to pcm failed");
        fclose(tempFile);
        return -1;
    }
    lame_t lame = lame_init();
    lame_set_in_samplerate(lame, 24000);

    lame_set_num_channels(lame, 1);
    lame_set_mode(lame, MONO);
    lame_set_quality(lame, 5);
    lame_init_params(lame);

    FILE *pcm = tempFile;
    fseek(pcm, 0, SEEK_SET);

    FILE *mp3 = fopen(dest_c, "wbe");
    int read, write;

    const int PCM_SIZE = 8192;
    const int MP3_SIZE = 8192;
    short int pcm_buffer[PCM_SIZE];
    unsigned char mp3_buffer[MP3_SIZE];

    do {
        read = static_cast<int>(fread(pcm_buffer, sizeof(short int), PCM_SIZE, pcm));
        if (read == 0) {
            write = lame_encode_flush(lame, mp3_buffer, MP3_SIZE);
        } else {
            write = lame_encode_buffer(lame, pcm_buffer, nullptr, read, mp3_buffer, MP3_SIZE);
        }

        fwrite(mp3_buffer, 1, static_cast<size_t>(write), mp3);
    } while (read != 0);

    lame_close(lame);
    fclose(mp3);
    fclose(pcm);

    return 1;
}

#ifdef __cplusplus
}
#endif