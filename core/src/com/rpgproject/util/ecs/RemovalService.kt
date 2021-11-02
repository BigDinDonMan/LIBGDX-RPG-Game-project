package com.rpgproject.util.ecs

import com.artemis.Entity
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld

object RemovalService {

    private lateinit var ecsWorld: EcsWorld
    private lateinit var physicsWorld: PhysicsWorld

    fun init(ecsWorld: EcsWorld, physicsWorld: PhysicsWorld) {
        this.ecsWorld = ecsWorld
        this.physicsWorld = physicsWorld
    }

    private val entityQueue = mutableListOf<Entity>()
    private val entityIdsQueue = mutableListOf<Int>()
    private val bodyQueue = mutableListOf<Body>()
    private val jointQueue = mutableListOf<Joint>()

    fun process() {
        entityQueue.forEach(ecsWorld::deleteEntity)
        entityIdsQueue.forEach(ecsWorld::delete)
        bodyQueue.forEach(physicsWorld::destroyBody)
        jointQueue.forEach(physicsWorld::destroyJoint)
        entityQueue.clear()
        bodyQueue.clear()
        jointQueue.clear()
        entityIdsQueue.clear()
    }

    fun mark(e: Entity) {
        entityQueue += e
    }

    fun mark(id: Int) {
        entityIdsQueue += id
    }

    fun mark(j: Joint) {
        jointQueue += j
    }

    fun mark(b: Body) {
        bodyQueue += b
    }
}