package com.rpgproject.util.math

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
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

fun getDirection(x1: Float, y1: Float, x2: Float, y2: Float): Vector2 {
    return Vector2(x2 - x1, y2 - y1)
}

fun getDirection(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Vector3 {
    return Vector3(x2 - x1, y2 - y1, z2 - z1)
}

fun getRotationAngle(direction: Vector2): Float {
    return getRotationAngle(direction.x, direction.y)
}

fun getRotationAngle(directionX: Float, directionY: Float): Float {
    return MathUtils.atan2(directionY, directionX) * MathUtils.radiansToDegrees
}

//todo: implement voronoi and perlin noise algorithms (maybe some others?)