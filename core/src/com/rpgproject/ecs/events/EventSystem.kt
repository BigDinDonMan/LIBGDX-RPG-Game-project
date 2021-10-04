package com.rpgproject.ecs.events

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import java.util.LinkedList

abstract class EventSystem : IteratingSystem(Family.all().get()) {

    protected val queuedEvents = LinkedList<Event>()
    protected val currentEventBatch = LinkedList<Event>()

    override fun processEntity(entity: Entity?, deltaTime: Float) {} //dont do anything, this system only processes events
    override fun update(deltaTime: Float) {

        currentEventBatch.forEach(this::processEvent)

        currentEventBatch.clear().also { currentEventBatch += queuedEvents }
        queuedEvents.clear()
    }

    fun queueEvent(e: Event) {
        queuedEvents += e
    }

    //this function should be implemented by each of
    abstract fun processEvent(e: Event)
}