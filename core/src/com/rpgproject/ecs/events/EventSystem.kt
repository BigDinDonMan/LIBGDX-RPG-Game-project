package com.rpgproject.ecs.events

import java.util.*

abstract class EventSystem {

    protected val queuedEvents = LinkedList<Event>()
    protected val currentEventBatch = LinkedList<Event>()

//    override fun processEntity(entity: Entity?, deltaTime: Float) {} //dont do anything, this system only processes events
//    override fun update(deltaTime: Float) {
//
//        currentEventBatch.forEach(this::processEvent)
//
//        currentEventBatch.clear().also { currentEventBatch += queuedEvents }
//        queuedEvents.clear()
//    }

    fun queueEvent(e: Event) {
        queuedEvents += e
    }

    //this function should be implemented by each of
    abstract fun processEvent(e: Event)
}