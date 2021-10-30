package com.rpgproject.util.collections

fun <T> MutableList<in T>.set(indices: IntRange, vararg items: T) {
    var index = 0
    for (i in indices) {
        this[i] = items[index++]
    }
}

fun <T> Array<in T>.set(indices: IntRange, vararg items: T) {
    var index = 0
    for (i in indices) {
        this[i] = items[index++]
    }
}