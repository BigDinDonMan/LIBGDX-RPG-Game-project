package com.rpgproject.ecs.systems

import com.artemis.annotations.All
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.util.PhysicsWorld

@All(TransformComponent::class, RigidBodyComponent::class)
class PhysicsSystem(private val physicsWorld: PhysicsWorld, private val velocityIterations: Int = 6, private val positionIterations: Int = 2) : IteratingSystem() {

    private val TIME_STEP = 1 / 300f
    private var accumulator = 0f

    override fun begin() {
        val delta = Gdx.graphics.deltaTime
        accumulator += delta
        while (accumulator >= delta) {
            physicsWorld.step(TIME_STEP, velocityIterations, positionIterations)
            accumulator -= TIME_STEP
        }
    }

    override fun process(entityId: Int) {

    }
}