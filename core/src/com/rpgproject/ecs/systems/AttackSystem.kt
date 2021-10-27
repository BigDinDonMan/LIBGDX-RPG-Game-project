package com.rpgproject.ecs.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.rpgproject.ecs.components.AttackComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TagComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.AttackEvent
import com.rpgproject.util.PhysicsWorld
import net.mostlyoriginal.api.event.common.Subscribe
import java.util.*

@All(RigidBodyComponent::class, TransformComponent::class, AttackComponent::class, TagComponent::class)
class AttackSystem(val physicsWorld: PhysicsWorld) : BaseSystem() {

    @Wire
    var attackDataMapper: ComponentMapper<AttackComponent>? = null

    private val eventQueue = LinkedList<AttackEvent>()

    @Subscribe
    fun queueAttack(e: AttackEvent) = eventQueue.add(e)

    /*process all the events and deal damage/queue other events if necessary*/
    override fun processSystem() {
        eventQueue.forEach { print(it) }
        eventQueue.clear()
    }
}