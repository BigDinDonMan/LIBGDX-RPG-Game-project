package com.rpgproject.ecs.events

import kotlin.reflect.KClass

class EventFamily(vararg eventTypes: KClass<Event>) {
    private val eventTypes: Array<KClass<Event>> = Array(eventTypes.size) { i -> eventTypes[i] }

    operator fun contains(type: KClass<Event>) = type in eventTypes
}