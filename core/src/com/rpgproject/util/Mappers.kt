package com.rpgproject.util

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity

@Deprecated(message = "No longer using Ashley; switch to Artemis instead")
object Mappers {

    val classMappers = HashMap<Class<out Component>, ComponentMapper<out Component>>()

    init { //register base types like transform, etc. here

    }

    fun registerFor(_class: Class<out Component>) {
        classMappers[_class] = ComponentMapper.getFor(_class)
    }

    inline fun <reified T: Component> getComponent(e: Entity): T = classMappers.getValue(T::class.java).get(e) as T
}