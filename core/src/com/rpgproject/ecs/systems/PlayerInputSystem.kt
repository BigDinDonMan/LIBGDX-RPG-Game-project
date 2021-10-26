package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import net.mostlyoriginal.api.event.common.Subscribe

@All(PlayerComponent::class)
class PlayerInputSystem : IteratingSystem() {

    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var rigidBodyMapper: ComponentMapper<RigidBodyComponent>? = null

    @Wire
    var playerMapper: ComponentMapper<PlayerComponent>? = null

    var playerMoveDirection = Vector2()

    private val MAX_VELOCITY = Vector2(10f, 10f)

    @Subscribe
    fun getPlayerInput(e: PlayerInputEvent) {
        playerMoveDirection.set(e.lookDirectionX, e.lookDirectionY)
    }

    override fun process(entityId: Int) {
        val rigidBody = rigidBodyMapper!!.get(entityId)
        val player = playerMapper!!.get(entityId)
        //todo: increase friction/mass/linear damping because its really fast (or maybe clamp velocity?)
        rigidBody.physicsBody!!.applyLinearImpulse(
                playerMoveDirection.x * player.speed,
                playerMoveDirection.y * player.speed,
                rigidBody.physicsBody!!.position.x,
                rigidBody.physicsBody!!.position.y,
                true)
    }
}