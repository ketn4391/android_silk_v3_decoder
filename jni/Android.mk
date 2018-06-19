LOCAL_PATH := $(call my-dir)

C_INCLUDES = $(LOCAL_PATH)/include
include $(CLEAR_VARS)

LOCAL_MODULE    := libmp3lame
LOCAL_SRC_FILES := libmp3lame/bitstream.c \
    libmp3lame/fft.c \
    libmp3lame/id3tag.c \
    libmp3lame/mpglib_interface.c \
    libmp3lame/presets.c \
    libmp3lame/quantize.c \
    libmp3lame/reservoir.c \
    libmp3lame/tables.c \
    libmp3lame/util.c \
    libmp3lame/VbrTag.c \
    libmp3lame/encoder.c \
    libmp3lame/gain_analysis.c \
    libmp3lame/lame.c \
    libmp3lame/newmdct.c \
    libmp3lame/psymodel.c \
    libmp3lame/quantize_pvt.c \
    libmp3lame/set_get.c \
    libmp3lame/takehiro.c \
    libmp3lame/vbrquantize.c \
    libmp3lame/version.c

LOCAL_C_INCLUDES += $(C_INCLUDES)
include $(BUILD_STATIC_LIBRARY)


# second lib, which will depend on and include the first one
#
include $(CLEAR_VARS)

LOCAL_MODULE := libsilkx
LOCAL_SRC_FILES := SKP_Silk_dec_API.c \
    SKP_Silk_create_init_destroy.c \
    SKP_Silk_decoder_set_fs.c \
    SKP_Silk_tables_NLSF_CB0_10.c \
    SKP_Silk_tables_NLSF_CB1_10.c \
    SKP_Silk_tables_NLSF_CB0_16.c \
    SKP_Silk_tables_NLSF_CB1_16.c \
    SKP_Silk_tables_other.c \
    SKP_Silk_CNG.c \
    SKP_Silk_NLSF2A_stable.c \
    SKP_Silk_NLSF2A.c \
    SKP_Silk_bwexpander_32.c \
    SKP_Silk_LSF_cos_table.c \
    SKP_Silk_bwexpander.c \
    SKP_Silk_LPC_inv_pred_gain.c \
    SKP_Silk_LPC_synthesis_filter.c \
    SKP_Silk_LPC_synthesis_order16.c \
    SKP_Silk_PLC.c \
    SKP_Silk_sum_sqr_shift.c \
    SKP_Silk_decode_frame.c \
    SKP_Silk_range_coder.c \
    SKP_Silk_decode_parameters.c \
    SKP_Silk_tables_pitch_lag.c \
    SKP_Silk_tables_type_offset.c \
    SKP_Silk_gain_quant.c \
    SKP_Silk_lin2log.c \
    SKP_Silk_NLSF_MSVQ_decode.c \
    SKP_Silk_NLSF_stabilize.c \
    SKP_Silk_sort.c \
    SKP_Silk_decode_pitch.c \
    SKP_Silk_pitch_est_tables.c \
    SKP_Silk_tables_LTP.c \
    SKP_Silk_tables_gain.c \
    SKP_Silk_decode_pulses.c \
    SKP_Silk_tables_pulses_per_block.c \
    SKP_Silk_code_signs.c \
    SKP_Silk_tables_sign.c \
    SKP_Silk_shell_coder.c \
    SKP_Silk_biquad.c \
    SKP_Silk_decode_core.c \
    SKP_Silk_MA.c \
    SKP_Silk_resampler.c \
    SKP_Silk_resampler_private_down4.c \
    SKP_Silk_resampler_rom.c \
    SKP_Silk_resampler_private_copy.c \
    SKP_Silk_resampler_private_down_FIR.c \
    SKP_Silk_resampler_private_AR2.c \
    SKP_Silk_resampler_down2.c \
    SKP_Silk_resampler_private_up2_HQ.c \
    SKP_Silk_resampler_private_IIR_FIR.c \
    SKP_Silk_resampler_private_ARMA4.c \
    SKP_Silk_resampler_private_up4.c \
    SKP_Silk_resampler_up2.c \
    SKP_Silk_log2lin.c \
    silk.c \
    decoder.cpp


LOCAL_C_INCLUDES += $(C_INCLUDES)
LOCAL_LDLIBS += -llog

LOCAL_STATIC_LIBRARIES := libmp3lame

include $(BUILD_SHARED_LIBRARY)
