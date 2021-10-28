package com.rpgproject.ecs.systems

import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.badlogic.gdx.Gdx
import com.rpgproject.ecs.components.AttackComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TagComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.AttackEvent
import com.rpgproject.util.PhysicsWorld
import com.rpgproject.util.physics.AttackRayCastCallback
import net.mostlyoriginal.api.event.common.Subscribe
import java.util.*

@All(RigidBodyComponent::class, TransformComponent::class, AttackComponent::class, TagComponent::class)
class AttackSystem(val physicsWorld: PhysicsWorld) : BaseEntitySystem() {

    @Wire
    var attackDataMapper: ComponentMapper<AttackComponent>? = null

    private val eventQueue = LinkedList<AttackEvent>()
    private val attackRayCastCallback = AttackRayCastCallback()

    @Subscribe
    fun queueAttack(e: AttackEvent) = eventQueue.add(e)

    /*process all the events and deal damage/queue other events if necessary*/
    override fun processSystem() {
        val delta = Gdx.graphics.deltaTime
        eventQueue.forEach { e ->
            run {
                val attackComponent = attackDataMapper!!.get(e.entity)
                if (attackComponent.currentCooldown <= 0) {
                    //register attack & queue play animation event
//                physicsWorld.rayCast()
                    val currentCooldown = attackComponent.cooldowns.removeFirst()
                    attackComponent.cooldowns.add(currentCooldown)
                    attackComponent.currentCooldown = currentCooldown
                }
            }
        } //note: raycast in the look direction vector * attack range and then deal damage to each damagable entity
        //also reduce current cooldown & assign next one
        //also, apply knockback to hit enemies in the direction opposite of the look direction vector of the attack
        eventQueue.clear()

        val entities = subscription.entities
        for (i in 0..entities.size()) {
            val attackData = attackDataMapper!!.get(entities[i])
            if (attackData.currentCooldown > 0) {
                attackData.currentCooldown -= delta
            }
        }
    }
}