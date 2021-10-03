package com.rpgproject.ecs.events

//register systems and types of events they should listen for
object EventManager {

    fun <T: Event> registerSystem(system: EventSystem<T>) {

    }

    fun <T: Event> queueEvent(event: T) {
    }
}