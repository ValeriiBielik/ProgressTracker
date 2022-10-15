package com.bielik.progresstracker.utils.extensions

fun <T : Any, P : Any, RESULT> letBoth(first: T?, second: P?, onNotNull: (first: T, second: P) -> RESULT): RESULT? {
    return if (first != null && second != null) {
        onNotNull(first, second)
    } else {
        null
    }
}

fun <E> MutableCollection<E>.clearAndAddAll(replace: List<E>) {
    clear()
    addAll(replace)
}

fun <E> MutableCollection<E>.clearAndInit(replace: E) {
    clear()
    add(replace)
}

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
    return map {
        if (block(it)) newValue else it
    }
}
