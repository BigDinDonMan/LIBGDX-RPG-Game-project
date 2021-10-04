package com.rpgproject.ecs.events

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.ObjectMap
import kotlin.reflect.KClass


//register systems and types of events they should listen for
object EventManager {

    private val systemsEventData = ObjectMap<KClass<EventSystem>, Array<KClass<Event>>>()
    private val registeredSystems = ObjectMap<KClass<EventSystem>, EventSystem>()

    private val systemsList = ArrayList<EventSystem>()

    fun queueEvent(event: Event) {//perfoms filtering of systems to see which one should accept this event
        filterSystemsFor(event)
        systemsList.forEach { it.queueEvent(event) }
    }

    private fun filterSystemsFor(event: Event) {
        systemsList.clear()
        val eventClass = event::class
        systemsEventData.filter { eventClass in it.value }.forEach { e -> systemsList.add(registeredSystems[e.key]) }
    }

    private fun registerSystem(system: EventSystem, systemClass: KClass<EventSystem>, vararg listensFor: KClass<Event>) {
        systemsEventData.put(systemClass, arrayOf(*listensFor))
        registeredSystems.put(systemClass, system)
    }
}