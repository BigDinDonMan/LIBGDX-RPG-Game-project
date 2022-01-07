package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

//this class is obsolete for now
class PlayerComponent : Component() {
    var speed = 25f
    val lookDirection = Vector2()
}
