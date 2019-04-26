# documentation: https://d.android.com/studio/projects/add-native-code.html
cmake_minimum_required(VERSION 3.4.1)
SET(CMAKE_BUILE_TYPE DEBUG)

if (${ANDROID_ABI} STREQUAL arm)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -mfpu=vfp -mfloat-abi=softfp -fno-short-enums")
endif ()
set(CMAKE_CPP_FLAGS "${CMAKE_CPP_FLAGS}  -std=c++11")
SET(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl,-s")

#find_library(log-lib log)
#find_library(z-lib z)
#include_directories(jni/include)
#message("搜索结果:")
add_library(mp3lame STATIC jni/libmp3lame/bitstream.c
        jni/libmp3lame/fft.c
        jni/libmp3lame/id3tag.c
        jni/libmp3lame/mpglib_interface.c
        jni/libmp3lame/presets.c
        jni/libmp3lame/quantize.c
        jni/libmp3lame/reservoir.c
        jni/libmp3lame/tables.c
        jni/libmp3lame/util.c
        jni/libmp3lame/VbrTag.c
        jni/libmp3lame/encoder.c
        jni/libmp3lame/gain_analysis.c
        jni/libmp3lame/lame.c
        jni/libmp3lame/newmdct.c
        jni/libmp3lame/psymodel.c
        jni/libmp3lame/quantize_pvt.c
        jni/libmp3lame/set_get.c
        jni/libmp3lame/takehiro.c
        jni/libmp3lame/vbrquantize.c
        jni/libmp3lame/version.c
        )

target_include_directories(mp3lame PUBLIC jni/include/lame.h)

add_library(silkx SHARED

        jni/SKP_Silk_dec_API.c
        jni/SKP_Silk_create_init_destroy.c
        jni/SKP_Silk_decoder_set_fs.c
        jni/SKP_Silk_tables_NLSF_CB0_10.c
        jni/SKP_Silk_tables_NLSF_CB1_10.c
        jni/SKP_Silk_tables_NLSF_CB0_16.c
        jni/SKP_Silk_tables_NLSF_CB1_16.c
        jni/SKP_Silk_tables_other.c
        jni/SKP_Silk_CNG.c
        jni/SKP_Silk_NLSF2A_stable.c
        jni/SKP_Silk_NLSF2A.c
        jni/SKP_Silk_bwexpander_32.c
        jni/SKP_Silk_LSF_cos_table.c
        jni/SKP_Silk_bwexpander.c
        jni/SKP_Silk_LPC_inv_pred_gain.c
        jni/SKP_Silk_LPC_synthesis_filter.c
        jni/SKP_Silk_LPC_synthesis_order16.c
        jni/SKP_Silk_PLC.c
        jni/SKP_Silk_sum_sqr_shift.c
        jni/SKP_Silk_decode_frame.c
        jni/SKP_Silk_range_coder.c
        jni/SKP_Silk_decode_parameters.c
        jni/SKP_Silk_tables_pitch_lag.c
        jni/SKP_Silk_tables_type_offset.c
        jni/SKP_Silk_gain_quant.c
        jni/SKP_Silk_lin2log.c
        jni/SKP_Silk_NLSF_MSVQ_decode.c
        jni/SKP_Silk_NLSF_stabilize.c
        jni/SKP_Silk_sort.c
        jni/SKP_Silk_decode_pitch.c
        jni/SKP_Silk_pitch_est_tables.c
        jni/SKP_Silk_tables_LTP.c
        jni/SKP_Silk_tables_gain.c
        jni/SKP_Silk_decode_pulses.c
        jni/SKP_Silk_tables_pulses_per_block.c
        jni/SKP_Silk_code_signs.c
        jni/SKP_Silk_tables_sign.c
        jni/SKP_Silk_shell_coder.c
        jni/SKP_Silk_biquad.c
        jni/SKP_Silk_decode_core.c
        jni/SKP_Silk_MA.c
        jni/SKP_Silk_resampler.c
        jni/SKP_Silk_resampler_private_down4.c
        jni/SKP_Silk_resampler_rom.c
        jni/SKP_Silk_resampler_private_copy.c
        jni/SKP_Silk_resampler_private_down_FIR.c
        jni/SKP_Silk_resampler_private_AR2.c
        jni/SKP_Silk_resampler_down2.c
        jni/SKP_Silk_resampler_private_up2_HQ.c
        jni/SKP_Silk_resampler_private_IIR_FIR.c
        jni/SKP_Silk_resampler_private_ARMA4.c
        jni/SKP_Silk_resampler_private_up4.c
        jni/SKP_Silk_resampler_up2.c
        jni/SKP_Silk_log2lin.c
        jni/silk.c
        jni/decoder.cpp
#        jni/decoder.c
        )
#target_include_directories(silkx PUBLIC
#        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/jni/include>
#        $<INSTALL_INTERFACE:jni/include>  # <prefix>/include/mylib
#        )
target_include_directories(mp3lame PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/jni/include)


target_link_libraries(silkx
        mp3lame
        log
        z )

