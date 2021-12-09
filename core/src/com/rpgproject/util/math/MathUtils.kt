package com.rpgproject.util.math

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun clamp01(x: Float) = MathUtils.clamp(x, 0f, 1f)

fun distance(v1: Vector2, v2: Vector2): Float = distance(v1.x, v2.x, v1.y, v2.y)

fun distance(x1: Float, x2: Float, y1: Float, y2: Float): Float =
        sqrt((x1 - x2).pow(2f) + (y1 - y2).pow(2f))

fun Float.isClose(other: Float, eps: Float): Boolean {
    return abs(this - other) <= eps;
}

fun Double.isClose(other: Double, eps: Float): Boolean {
    return abs(this - other) <= eps;
}
