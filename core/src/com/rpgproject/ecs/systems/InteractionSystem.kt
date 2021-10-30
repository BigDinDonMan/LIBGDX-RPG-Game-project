package com.rpgproject.ecs.systems

import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.Entity
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.rpgproject.ecs.components.InteractableComponent
import com.rpgproject.ecs.components.TextureComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import com.rpgproject.util.assets.ShaderStorage
import com.rpgproject.util.math.distance
import net.mostlyoriginal.api.event.common.Subscribe

@All(InteractableComponent::class, TransformComponent::class, TextureComponent::class)
class InteractionSystem : BaseEntitySystem() {
    //todo: add outline color map depending on interactable type (e.g. yellow for pickups, blue for npcs, etc.)
    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var textureMapper: ComponentMapper<TextureComponent>? = null

    //note: try to inject player entity into this clusterfuck somehow
    //same with shaders/colors
    private var playerEntity: Entity? = null
    private val interactionRange: Float = 150f //filter out only the entities whose distances are less than this, then select minimum distance one

    @Subscribe
    fun handleInteraction(e: PlayerInputEvent) {

    }

    override fun processSystem() {
        // calculate distance from player for each of the entities and select the closest one
        // and then highlight it and handle interaction when E (keyboard)/X (gamepad) is pressed
        // set outline shader/highlight color in selected element's texture component
        // different interactables should have different border colors
        if (playerEntity == null || subscription.entities.isEmpty) return
        var closestEntityId = 0
        var smallestDistance = 1_000_000_000f
        val playerTransform = transformMapper!!.get(playerEntity)
        val playerCenter = playerTransform.center()
        for (i in 0 until subscription.entities.size()) {
            val interactableTransform = transformMapper!!.get(subscription.entities[i])
            val center = interactableTransform.center()
            val distance = distance(playerCenter.x, center.x, playerCenter.y, center.y)
            if (distance > interactionRange) {
                val farTextureComponent = textureMapper!!.get(subscription.entities[i])
                farTextureComponent?.shader = null
                continue
            }
            if (distance < smallestDistance) {
                smallestDistance = distance
                closestEntityId = subscription.entities[i]
            }
        }
        println(smallestDistance)
        if (closestEntityId == playerEntity?.id) return
        val textureComponent = textureMapper!!.get(closestEntityId)
        textureComponent?.shader = ShaderStorage["Outline"].apply { setUniformf("outlineColor", 1f, 0f, 0f) } //todo: set actual injected outline shader here
    }

    fun injectPlayerEntity(e: Entity) {
        this.playerEntity = e
    }
}