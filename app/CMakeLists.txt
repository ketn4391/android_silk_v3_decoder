# documentation: https://d.android.com/studio/projects/add-native-code.html
cmake_minimum_required(VERSION 3.4.1)
SET(CMAKE_BUILE_TYPE DEBUG)

find_library(log-lib log)

add_library(curl-prebuilt
        SHARED
        IMPORTED)
set_target_properties(
        curl-prebuilt
        PROPERTIES IMPORTED_LOCATION
        ./libs/${ANDROID_ABI}/libcurl.a)

if (${ANDROID_ABI} STREQUAL arm)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -mfpu=vfp -mfloat-abi=softfp -fno-short-enums")
endif ()
set(CMAKE_CPP_FLAGS "${CMAKE_CPP_FLAGS} -std=gnu++0x")
SET(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl,-s")

aux_source_directory(jni/reflection/ REFLECTION_SRC)
add_library(free-reflection SHARED  ${REFLECTION_SRC})

target_link_libraries(free-reflection  ${log-lib} )

include_directories(jni/include)
aux_source_directory(jni/encrypto/ ENCRYPTO_SRC)
add_library(JNIEncrypt SHARED ${ENCRYPTO_SRC})
#target_include_directories(JNIEncrypt PUBLIC jni/include)#相关头文件的目录
target_link_libraries(JNIEncrypt ${log-lib} z)