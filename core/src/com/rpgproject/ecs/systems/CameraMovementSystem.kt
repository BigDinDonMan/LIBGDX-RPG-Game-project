package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.One
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector3
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent

@One(PlayerComponent::class)
class CameraMovementSystem(val camera: Camera, val cameraSpeed: Float = 0.5f) : IteratingSystem() {

    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var rigidBodyMapper: ComponentMapper<RigidBodyComponent>? = null

    private var isShaking = false
    private var shakeMagnitude = 0.1f
    private var cameraPosition = Vector3()
    private var targetPosition = Vector3()

    override fun begin() {
        cameraPosition.set(camera.position)
    }

    override fun process(entityId: Int) {
        val transform = transformMapper!!.get(entityId)
        val rigidBody = rigidBodyMapper!!.get(entityId)
        val playerX = rigidBody.physicsBody!!.position.x
        val playerY = rigidBody.physicsBody!!.position.y
        targetPosition.set(playerX, playerY, transform.position.z)
        cameraPosition.interpolate(targetPosition, cameraSpeed, Interpolation.smooth2)
        camera.position.set(cameraPosition)
    }
}