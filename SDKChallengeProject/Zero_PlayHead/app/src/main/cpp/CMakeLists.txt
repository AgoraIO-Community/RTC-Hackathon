
cmake_minimum_required(VERSION 3.4.1)

add_library(
        apm-plugin-packet-processing
        SHARED
        apm-plugin-packet-processing.cpp)

include_directories(../cpp/include)

find_library(
        log-lib
        log)

target_link_libraries(
        apm-plugin-packet-processing
        ${log-lib})