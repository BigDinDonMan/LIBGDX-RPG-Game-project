package com.rpgproject.util.ecs

import com.badlogic.ashley.core.Engine

fun Engine.removeAllSystems() {
    val systems = this.systems.toArray()
    systems.forEach(this::removeSystem)
}