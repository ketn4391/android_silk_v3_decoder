# android_silk_v3_decoder
Convert silk v3 audio files (QQ &amp; WeChat voice files) to mp3 files on android.

please refer to this app, https://play.google.com/store/apps/details?id=com.pleasure.trace_wechat

#2019-04-26 更新构建系统为Cmake+Clang(NDK15(记不清是不是)以后官方默认),转换成kotlin编写,修改lame/machine.h报错的部分,修改silkx JNI入口参数错误问题,增加权限验证(Android7+),替换Android支持包为AndroidX


国内的可以搜索“微痕迹”，此应用集成了微信和ＱＱ语音的转成mp3的功能，另外提供管理微信ＱＱ图片语音文件的功能。
因为语音转换这部分参考了一些开源工程，特将此部分开源，回馈开源社区。
