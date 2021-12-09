package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.One
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.events.specific.CameraShakeEvent
import net.mostlyoriginal.api.event.common.Subscribe
import kotlin.random.Random

@One(PlayerComponent::class)
class CameraMovementSystem(val camera: Camera, val cameraSpeed: Float = 0.5f) : IteratingSystem() {

    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var rigidBodyMapper: ComponentMapper<RigidBodyComponent>? = null

    private var isShaking = false
    private var shakeMagnitude = 5.0
    private var shakeDuration = 0.5f
    private var currentShakeDuration = 0f
    private var interpolationPosition = Vector3()
    private var cameraPosition = Vector3()
    private var targetPosition = Vector3()

    @Subscribe
    fun startShaking(e: CameraShakeEvent) {
        isShaking = true
        shakeMagnitude = e.magnitude.toDouble()
        shakeDuration = e.duration
        currentShakeDuration = 0f
        interpolationPosition.set(cameraPosition)
    }

    override fun begin() {
        if (!isShaking) {
            cameraPosition.set(camera.position)
        }
    }

    override fun process(entityId: Int) {
        val transform = transformMapper!!.get(entityId)
        val rigidBody = rigidBodyMapper!!.get(entityId)
        val playerX = rigidBody.physicsBody!!.position.x
        val playerY = rigidBody.physicsBody!!.position.y
        targetPosition.set(playerX, playerY, transform.position.z)
        if (isShaking) {
            interpolationPosition.interpolate(targetPosition, cameraSpeed, Interpolation.smooth2)
            cameraPosition.set(interpolationPosition)
            cameraPosition.add(
                    Random.nextDouble(-shakeMagnitude, shakeMagnitude).toFloat(),
                    Random.nextDouble(-shakeMagnitude, shakeMagnitude).toFloat(),
                    0f
            )
            shakeMagnitude = MathUtils.lerp(shakeMagnitude.toFloat(), 0f, 0.05f).toDouble()
            currentShakeDuration += Gdx.graphics.deltaTime
            if (currentShakeDuration >= shakeDuration) {
                currentShakeDuration = 0f
                isShaking = false
            }
        } else {
//            cameraPosition.interpolate(targetPosition, cameraSpeed, Interpolation.smooth2)
        }
        camera.position.set(cameraPosition)
    }
}