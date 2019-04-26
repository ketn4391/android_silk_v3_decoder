package com.ketian.android.silkv3

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.util.*

/**
 * Created by ketian.
 */
class PathUtils private constructor(context: Context) {
    var exportDir: String? = null
        private set
    var voice_wechat_paths: MutableList<String>
    var voice_qq_paths: List<String>

    init {
        context.toString()
        voice_wechat_paths = ArrayList()
        voice_qq_paths = ArrayList()
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Log.e("doInBackground","哪个快?")
            val dir = Environment.getExternalStorageDirectory()
            val f = File("${dir.path}/tencent/MicroMsg/")
            var interrupt = false
            if (f.exists() &&  f.isDirectory) {
                val files = f.listFiles()
                if (files != null && files.isNotEmpty()) {
                    for (f0 in files) {
                        if (f0.isDirectory && f0.name.length > 24) {
                            voice_wechat_paths.add(f0.absolutePath + "/voice2")
                        }
                    }
                } else {
                    interrupt = true
                }
            }
            if (!interrupt) {
                val exportDir = File(dir, "silkv3_mp3")
                if (!exportDir.exists()) {
                    exportDir.mkdirs()
                }
                this.exportDir = exportDir.absolutePath
            }
        }
    }

    companion object {
        private var instance: PathUtils? = null

        private fun createUtil(context: Context): PathUtils {
            if (instance == null) {
                instance = PathUtils(context)
            }
            return instance!!
        }

        fun getVoiceFiles_WeChat(context: Context): List<String> {
            return createUtil(context).voice_wechat_paths
        }

        fun getVoiceFiles_QQ(context: Context): List<String> {
            return createUtil(context).voice_qq_paths
        }

        fun getExportDir(context: Context): String? {
            return createUtil(context).exportDir
        }
    }
}
