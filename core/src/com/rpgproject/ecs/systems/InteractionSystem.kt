package com.rpgproject.ecs.systems

import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.Entity
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.Color
import com.rpgproject.ecs.components.*
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import com.rpgproject.inventory.Inventory
import com.rpgproject.inventory.InventoryItem
import com.rpgproject.util.GLType
import com.rpgproject.util.assets.ShaderStorage
import com.rpgproject.util.collections.set
import com.rpgproject.util.math.distance
import net.mostlyoriginal.api.event.common.Subscribe

@All(InteractableComponent::class, TransformComponent::class, TextureComponent::class)
class InteractionSystem : BaseEntitySystem() {
    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var interactionMapper: ComponentMapper<InteractableComponent>? = null

    @Wire
    var textureMapper: ComponentMapper<TextureComponent>? = null

    @Wire
    var inventoryItemMapper: ComponentMapper<InventoryItemComponent>? = null

    @Wire
    var shaderMapper: ComponentMapper<ShaderComponent>? = null

    private val interactablesColorMap = HashMap<InteractableComponent.InteractableObjectType, Color>()

    private var playerEntity: Entity? = null
    private val interactionRange: Float = 25f //filter out only the entities whose distances are less than this, then select minimum distance one

    private var playerInteracted = false

    init {
        interactablesColorMap[InteractableComponent.InteractableObjectType.PICKUP] = Color.CHARTREUSE
    }

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
        val interactableComponent = interactionMapper!!.get(closestEntityId)
        if (shaderComponent != null) {
            shaderComponent.shader = ShaderStorage["Outline"]
            val args = shaderComponent.shaderParams.getValue(GLType.VEC3).getValue("outlineColor")
            val color = interactablesColorMap.getValue(interactableComponent.interactableType)
            args.set(0 until 3, color.r, color.g, color.b)
        }

        if (playerInteracted) {
            playerInteracted = false

            // if its a pickup - add it to inventory if possible
            // if its an npc - show dialogue window
            // if its a lever/button/something similar - run its associated action
            when (interactableComponent.interactableType) {
                //note: if its a pickup then it has to have inventory item component and texture component instances
                InteractableComponent.InteractableObjectType.PICKUP -> {
                    println("picking up!")
                    val itemTextureComp = textureMapper!!.get(closestEntityId)
                    val itemComp = inventoryItemMapper!!.get(closestEntityId)
                    if (itemComp != null && itemTextureComp?.texture != null) {
                        val item = InventoryItem(itemComp.name, itemComp.description, itemTextureComp.texture!!)
                        if (Inventory.addItem(item)) {
                            //remove entity from the game
                            world.delete(closestEntityId)
                        }
                    }
                }
            }
        }
    }

    fun injectPlayerEntity(e: Entity) {
        this.playerEntity = e
    }
}