package com.rpgproject.ecs.systems

import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.Entity
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.rpgproject.ecs.components.InteractableComponent
import com.rpgproject.ecs.components.ShaderComponent
import com.rpgproject.ecs.components.TextureComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import com.rpgproject.util.GLType
import com.rpgproject.util.assets.ShaderStorage
import com.rpgproject.util.collections.set
import com.rpgproject.util.math.distance
import net.mostlyoriginal.api.event.common.Subscribe

@All(InteractableComponent::class, TransformComponent::class, TextureComponent::class)
class InteractionSystem : BaseEntitySystem() {
    //todo: add outline color map depending on interactable type (e.g. yellow for pickups, blue for npcs, etc.)
    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var interactionMapper: ComponentMapper<InteractableComponent>? = null

    @Wire
    var textureMapper: ComponentMapper<TextureComponent>? = null

    @Wire
    var shaderMapper: ComponentMapper<ShaderComponent>? = null

    //note: try to inject player entity into this clusterfuck somehow
    //same with shaders/colors
    private var playerEntity: Entity? = null
    private val interactionRange: Float = 150f //filter out only the entities whose distances are less than this, then select minimum distance one

    private var playerInteracted = false

    @Subscribe
    fun interact(e: PlayerInputEvent) {
        playerInteracted = e.interaction
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
                val farShaderComponent = shaderMapper!!.get(subscription.entities[i])
                farShaderComponent?.shader = null
                continue
            }
            if (distance < smallestDistance) {
                smallestDistance = distance
                closestEntityId = subscription.entities[i]
            }
        }
        if (closestEntityId == playerEntity?.id) return
        val shaderComponent = shaderMapper!!.get(closestEntityId)
        if (shaderComponent != null) {
            val outlineShader = ShaderStorage["Outline"]
            shaderComponent.shader = outlineShader
            //defaults wont work if we use indices; thats why we need to use getValue instead
            //todo: fix this goddamn default value map because it doesn't actually set a value at the missing key when it returns a default value
            val args = shaderComponent.shaderParams.getValue(GLType.VEC3).getValue("outlineColor")
            args.set(0 until 3, 1.0f, 0f, 0f)
        }

        if (playerInteracted) {
            playerInteracted = false

            val interactableComponent = interactionMapper!!.get(closestEntityId)
            when (interactableComponent.interactableType) {
                InteractableComponent.InteractableObjectType.PICKUP -> println("picking up!")
            }
        }
    }

    fun injectPlayerEntity(e: Entity) {
        this.playerEntity = e
    }
}