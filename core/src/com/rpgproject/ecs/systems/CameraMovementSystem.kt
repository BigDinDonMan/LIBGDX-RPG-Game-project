package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.One
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.MathUtils
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent

@One(PlayerComponent::class)
class CameraMovementSystem(val camera: Camera, val cameraSpeed: Float = 0.5f) : IteratingSystem() {

    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var rigidBodyMapper: ComponentMapper<RigidBodyComponent>? = null

    override fun process(entityId: Int) {
        val rigidBody = rigidBodyMapper!!.get(entityId)
        val cameraX = camera.position.x
        val cameraY = camera.position.y
        val playerX = rigidBody.physicsBody!!.position.x
        val playerY = rigidBody.physicsBody!!.position.y

        camera.position.set(
                MathUtils.lerp(cameraX, playerX, cameraSpeed),
                MathUtils.lerp(cameraY, playerY, cameraSpeed),
                camera.position.z
        )
    }
}