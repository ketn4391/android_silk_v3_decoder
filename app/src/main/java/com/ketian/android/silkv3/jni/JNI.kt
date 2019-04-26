package com.ketian.android.silkv3.jni

/**
 * Created by ketian on 16-10-10.
 */

object JNI {

    init {
        System.loadLibrary("silkx")
    }

    external fun convert(src: String, dest: String, tmpfile: String): Int

}
