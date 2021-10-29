package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.math.Vector3

class TransformComponent : Component() {
    val position = Vector3()
    val rotation = Vector3()
    val size = Vector3()
    val center = Vector3()

    fun width() = size.x
    fun height() = size.y

    fun center() = center.apply { set(position.x + width() / 2, position.y + height() / 2, position.z) }
}