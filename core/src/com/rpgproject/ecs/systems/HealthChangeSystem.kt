package com.rpgproject.ecs.systems

import com.rpgproject.ecs.components.HealthComponent
import com.rpgproject.ecs.events.Event
import com.rpgproject.ecs.events.EventSystem
import com.rpgproject.ecs.events.HealthChangeEvent
import com.rpgproject.ecs.events.PlayerInputEvent
import com.rpgproject.util.Mappers

class HealthChangeSystem : EventSystem() {

    override fun processEvent(e: Event) {
        when (e) { //this is kinda stupid
            is HealthChangeEvent -> processEventInternal(e)
        }
    }

    fun processEventInternal(e: HealthChangeEvent) {
        val healthComp = Mappers.getComponent<HealthComponent>(e.targetEntity)
        healthComp.health += e.healthChange
    }
}