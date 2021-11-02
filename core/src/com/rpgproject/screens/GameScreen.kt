package com.rpgproject.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.systems.InteractionSystem
import com.rpgproject.input.KeyboardHandler
import com.rpgproject.ui.InventoryWindow
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import com.rpgproject.util.ecs.RemovalService
import com.rpgproject.util.heightF
import com.rpgproject.util.ui.update
import com.rpgproject.util.widthF
import ktx.app.KtxScreen
import net.mostlyoriginal.api.event.common.EventSystem

class GameScreen(private val ecsWorld: EcsWorld, private val physicsWorld: PhysicsWorld, private val eventSystem: EventSystem, private val camera: Camera) : KtxScreen {

    private val viewport = StretchViewport(Gdx.graphics.widthF(), Gdx.graphics.heightF(), camera)
    private val stage = Stage(viewport)
    private val inventoryWindow = InventoryWindow("Inventory", Skin(Gdx.files.internal("skins/uiskin.json")))

    init {
        setupUI()
        spawnTestEntities()
    }

    private fun spawnTestEntities() {
        val playerEntity = ecsWorld.create()
        ecsWorld.getSystem(InteractionSystem::class.java).injectPlayerEntity(ecsWorld.getEntity(playerEntity))
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

        Gdx.input.inputProcessor = InputMultiplexer(KeyboardHandler(ecsWorld.getEntity(playerEntity), eventSystem), stage)
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun render(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            inventoryWindow.isVisible = !inventoryWindow.isVisible
        }
        stage.update(delta)
        ecsWorld.setDelta(delta).apply { ecsWorld.process() }
        RemovalService.process()
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)

    private fun setupUI() {
        stage.addActor(inventoryWindow)
        inventoryWindow.setPosition(
                Gdx.graphics.widthF() / 2 - inventoryWindow.width / 2,
                Gdx.graphics.heightF() / 2 - inventoryWindow.height / 2)
        inventoryWindow.isVisible = false
    }
}