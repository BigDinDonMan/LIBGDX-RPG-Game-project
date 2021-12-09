package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.math.Vector3

class TransformComponent : Component() {
    val position = Vector3()
    val size = Vector3()
    val origin = Vector3()
    val centerRelative = Vector3()
    var rotationAngle = 0f

    fun width() = size.x
    fun height() = size.y

    fun center() = centerRelative.apply { set(position.x + width() / 2, position.y + height() / 2, position.z) }
    fun origin() = origin.apply { set(width() / 2, height() / 2, origin.z) }
}