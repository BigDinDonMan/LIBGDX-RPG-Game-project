package com.rpgproject.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.input.KeyboardHandler
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import ktx.app.KtxScreen
import net.mostlyoriginal.api.event.common.EventSystem

// same here, add camera & viewport to handle resizing
class GameScreen(private val ecsWorld: EcsWorld, private val physicsWorld: PhysicsWorld, private val eventSystem: EventSystem, private val camera: Camera) : KtxScreen {

    private val viewport = StretchViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)
//    private val testTexture = Texture(Gdx.files.internal("shaders/outline-test-texture.png"))
//    private val spriteBatch = SpriteBatch()
//    private val outlineShader = ShaderProgram(Gdx.files.internal("shaders/outline-vertex.glsl"), Gdx.files.internal("shaders/outline-fragment.glsl"))

    init {
        spawnTestEntities()
//        spriteBatch.shader = outlineShader;
    }

    private fun spawnTestEntities() {
        val playerEntity = ecsWorld.create()
        val transform = TransformComponent()
        ecsWorld.edit(playerEntity).add(transform.apply {
            size.set(50f, 50f, 0f)
        }).add(RigidBodyComponent().apply {
            val bodyDef = BodyDef().apply {
                gravityScale = 0f
                linearDamping = 6f
                type = BodyDef.BodyType.DynamicBody
            }
            val shape = PolygonShape().apply { setAsBox(transform.width() / 2, transform.height() / 2) }
            val body = physicsWorld.createBody(bodyDef)
            val fixtureDef = FixtureDef().apply {
                this.shape = shape
                friction = 2f
            }
            physicsBody = body.apply { createFixture(fixtureDef) }
            shape.dispose()
        }).add(PlayerComponent())

        Gdx.input.inputProcessor = InputMultiplexer(KeyboardHandler(ecsWorld.getEntity(playerEntity), eventSystem))
    }

    override fun dispose() {
    }

    override fun render(delta: Float) {
        ecsWorld.setDelta(delta).apply { ecsWorld.process() }
//        spriteBatch.begin()
//        outlineShader.setUniformf("outlineColor", 1f, 0f, 0f)
//        spriteBatch.draw(testTexture, 50f, 50f)
//        spriteBatch.end()
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)
}