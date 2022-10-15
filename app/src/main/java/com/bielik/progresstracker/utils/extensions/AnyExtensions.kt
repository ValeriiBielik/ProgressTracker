package com.bielik.progresstracker.utils.extensions

val Any.genericTag: String
    get() = this::class.java.simpleName

val <T> T.exhaustive: T
    get() = this
