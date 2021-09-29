package com.rpgproject.util

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity

object Mappers {

    private val classMappers = HashMap<Class<out Component>, ComponentMapper<out Component>>()

    init { //register base types like transform, etc. here

    }

    fun registerFor(_class: Class<out Component>) {
        classMappers[_class] = ComponentMapper.getFor(_class)
    }

    fun getComponent(e: Entity, _class: Class<out Component>) = classMappers[_class]?.get(e)
}