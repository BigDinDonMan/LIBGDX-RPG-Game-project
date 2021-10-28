package com.rpgproject.ecs.systems

import com.artemis.BaseEntitySystem
import com.artemis.annotations.All
import com.rpgproject.ecs.components.InteractableComponent
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import net.mostlyoriginal.api.event.common.Subscribe

@All(InteractableComponent::class)
class InteractionSystem : BaseEntitySystem() {


    @Subscribe
    fun handleInteraction(e: PlayerInputEvent) {

    }

    override fun processSystem() {

    }
}