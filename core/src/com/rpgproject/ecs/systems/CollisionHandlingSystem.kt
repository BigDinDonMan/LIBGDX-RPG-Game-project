package com.rpgproject.ecs.systems

import com.artemis.BaseSystem
import com.rpgproject.ecs.events.EntityEvent
import net.mostlyoriginal.api.event.common.Subscribe

class CollisionHandlingSystem : BaseSystem() {

    override fun begin() {

    }

    override fun processSystem() {
    }

    override fun end() {

    }

    @Subscribe
    fun receiveEvent(e: EntityEvent) {
        println(e::class)
    }
}