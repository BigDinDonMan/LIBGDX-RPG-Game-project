package com.rpgproject.ecs.systems

import com.artemis.BaseSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.rpgproject.util.PhysicsWorld

class PhysicsDebugSystem(private val physicsWorld: PhysicsWorld, private val camera: Camera) : BaseSystem() {

    private val renderer = Box2DDebugRenderer()

    override fun processSystem() = renderer.render(physicsWorld, camera.combined)
}