package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.CameraShakeEvent
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import kotlin.random.Random

@All(PlayerComponent::class)
class PlayerInputSystem : IteratingSystem() {

    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var rigidBodyMapper: ComponentMapper<RigidBodyComponent>? = null

    @Wire
    var playerMapper: ComponentMapper<PlayerComponent>? = null

    @Wire
    var eventSystem: EventSystem? = null

    var playerMoveDirection = Vector2()

    var dodge = false
    var attack = false

    @Subscribe
    fun getPlayerInput(e: PlayerInputEvent) {
        playerMoveDirection.set(e.lookDirectionX, e.lookDirectionY)
        dodge = e.dodge
        attack = e.attack
    }

    override fun process(entityId: Int) {
        val rigidBody = rigidBodyMapper!!.get(entityId)
        val player = playerMapper!!.get(entityId)
        rigidBody.physicsBody!!.applyLinearImpulse(
                playerMoveDirection.x * player.speed,
                playerMoveDirection.y * player.speed,
                rigidBody.physicsBody!!.position.x,
                rigidBody.physicsBody!!.position.y,
                true)
        //todo: handle player dodge here (add impulse and block spacebar key)
        if (dodge) {
            dodge = false
        }
        if (attack) {
            eventSystem?.dispatch(CameraShakeEvent(2f, Random.nextFloat() * 5f))
            attack = false
        }
    }
}