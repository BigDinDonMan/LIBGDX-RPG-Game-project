package com.rpgproject.ecs.systems

import com.artemis.BaseEntitySystem
import com.artemis.annotations.All
import com.rpgproject.ecs.components.InteractableComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import net.mostlyoriginal.api.event.common.Subscribe

@All(InteractableComponent::class, TransformComponent::class)
class InteractionSystem : BaseEntitySystem() {

    private val interactionRange: Float = 150f //filter out only the entities whose distances are less than this, then select minimum distance one

    @Subscribe
    fun handleInteraction(e: PlayerInputEvent) {

    }

    override fun processSystem() {
        // calculate distance from player for each of the entities and select the closest one
        // and then highlight it and handle interaction when E (keyboard)/X (gamepad) is pressed
        // set outline shader/highlight color in selected element's texture component
        // different interactables should have different border colors
        for (i in 0..subscription.entities.size()) {

        }
    }
}