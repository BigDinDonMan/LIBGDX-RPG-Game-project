package com.rpgproject.ecs.systems

import com.rpgproject.ecs.events.Event
import com.rpgproject.ecs.events.EventSystem
import com.rpgproject.ecs.events.PlayerInputEvent

class PlayerInputSystem : EventSystem() {

    fun processEventInternal(e: PlayerInputEvent) {

    }

    override fun processEvent(e: Event) {
        when (e) {
            is PlayerInputEvent -> processEventInternal(e)
        }
    }
}