package com.rpgproject.ecs.systems

import com.rpgproject.ecs.components.HealthComponent
import com.rpgproject.ecs.events.Event
import com.rpgproject.ecs.events.EventSystem
import com.rpgproject.ecs.events.HealthChangeEvent
import com.rpgproject.util.Mappers

class HealthChangeSystem : EventSystem() {
    override fun processEvent(e: Event) {
        when (e) {
            is HealthChangeEvent -> processEventInternal(e)
        }
    }

    fun processEventInternal(e: HealthChangeEvent) {
        val healthComp = Mappers.getComponent<HealthComponent>(e.targetEntity)
        println("before: ${healthComp.health}")
        healthComp.health += e.healthChange
        println("after: ${healthComp.health}")
    }
}