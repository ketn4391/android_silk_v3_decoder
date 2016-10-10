package com.ketian.android.silkv3;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ketian.
 */
public class PathUtils {

    public static List<String> voice_wechat_paths;
    public static List<String> voice_qq_paths;

    private static String export_dir;

    public static void init(Context context) {
        voice_wechat_paths = new ArrayList<>();
        voice_qq_paths = new ArrayList<>();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = Environment.getExternalStorageDirectory();
            File f = new File(dir + "/tencent/MicroMsg");

            if (f.exists() && f.canRead() && f.isDirectory()) {
                File[] files = f.listFiles();
                if (files == null || files.length == 0) {
                    return;
                }

                for (File f0 : files) {
                    if (f0.isDirectory() && f0.getName().length() > 24) {
                        voice_wechat_paths.add(f0.getAbsolutePath() + "/voice2");
                    }
                }
            }

            File exportDir = new File(dir, "silkv3_mp3");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            export_dir = exportDir.getAbsolutePath();
        }
    }

    public static List<String> getVoiceFiles_WeChat(Context context) {
        return voice_wechat_paths;
    }

    public static List<String> getVoiceFiles_QQ(Context context) {
        return voice_qq_paths;
    }

    public static String getExportDir() {
        return export_dir;
    }
}
