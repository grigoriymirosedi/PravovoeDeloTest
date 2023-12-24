package com.example.pravovoedelotest.utils

class Utils {
}

fun String.toRawPhoneNumber(): String {
    return "7" + this
        .replace("+", "")
        .replace(" ", "")
        .replace("(", "")
        .replace(")", "")
        .replace("-", "")
}