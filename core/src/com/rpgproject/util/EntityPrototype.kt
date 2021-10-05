package com.rpgproject.util

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.ObjectMap

object EntityPrototype {
    private val prototypes = ObjectMap<String, Entity>()

    fun newInstance(entityKey: String): Entity {
        return null as Entity
    }

    fun add(entityKey: String, prototype: Entity) {
        prototypes.put(entityKey, prototype)
    }
}