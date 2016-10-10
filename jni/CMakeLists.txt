cmake_minimum_required(VERSION 2.8.4)
project(com.ketian.android.silkv3.jni)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
include_directories("/home/ketian/tools/android-ndk-r10e/platforms/android-21/arch-arm/usr/include")
include_directories("include")
include_directories("libmp3lame")

file(GLOB foo_sources decoder.cpp *.c *.h include/*.h libmp3lame/*.h libmp3lame/*.c)

set(SOURCE_FILES ${foo_sources})
add_executable(com.ketian.android.silkv3.jni ${SOURCE_FILES})