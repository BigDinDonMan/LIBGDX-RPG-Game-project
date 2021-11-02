package com.rpgproject.ecs.components

import com.artemis.Component


class AnimationComponent : Component() {
    var elapsedTime = 0f
    val animations = mutableMapOf<String, Any>()
    var currentKey = ""
}