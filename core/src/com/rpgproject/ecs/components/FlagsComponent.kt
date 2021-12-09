package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.utils.ObjectMap

class FlagsComponent : Component() {
    val flags = ObjectMap<String, Boolean>()
}