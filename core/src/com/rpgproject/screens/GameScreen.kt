package com.rpgproject.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerMapping
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.RigidBodyComponent
import com.rpgproject.ecs.components.TextureComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.ecs.systems.InteractionSystem
import com.rpgproject.input.GamePadHandler
import com.rpgproject.input.GamePadUIHandler
import com.rpgproject.input.KeyboardHandler
import com.rpgproject.input.KeyboardUIHandler
import com.rpgproject.ui.AnimatedCountdownLabel
import com.rpgproject.ui.InventoryWindow
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import com.rpgproject.util.ecs.RemovalService
import com.rpgproject.util.heightF
import com.rpgproject.util.math.rotationAngleTo
import com.rpgproject.util.ui.simulateClick
import com.rpgproject.util.ui.update
import com.rpgproject.util.widthF
import ktx.app.KtxScreen
import net.mostlyoriginal.api.event.common.EventSystem
import kotlin.properties.Delegates

class GameScreen(private val ecsWorld: EcsWorld, private val physicsWorld: PhysicsWorld, private val eventSystem: EventSystem, private val camera: Camera) : KtxScreen {

    private val viewport = StretchViewport(Gdx.graphics.widthF(), Gdx.graphics.heightF())
    private val stage = Stage(viewport)
    private val inventoryWindow = InventoryWindow("Inventory", Skin(Gdx.files.internal("skins/uiskin.json")))
    private val moneyDisplay = AnimatedCountdownLabel("", 0)
    //this needs to be first; if we return false from buttonDown then it gets passed to the other listener
    private val gamePadUIHandler = GamePadUIHandler(stage)
    private val gamePadHandler = GamePadHandler(eventSystem)
    private val keyboardUIHandler = KeyboardUIHandler(stage)

    private val testTexture = Texture(Gdx.files.internal("arrow.png"))
    private var testEntity by Delegates.notNull<Int>()
    private val testTransform = TransformComponent();

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

        Gdx.input.inputProcessor = InputMultiplexer(
            keyboardUIHandler,
            KeyboardHandler(ecsWorld.getEntity(playerEntity), eventSystem),
            stage
        )
        gamePadHandler.injectPlayerEntity(ecsWorld.getEntity(playerEntity))

        testEntity = ecsWorld.create()
        ecsWorld.edit(testEntity).add(testTransform.apply {
            position.set(50f, 50f, 0f)
            size.set(testTexture.width.toFloat(), testTexture.height.toFloat(), 0f)
        }).add(TextureComponent().apply { texture = testTexture })
        println(testTransform.size)
        println(testTransform.origin())
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun render(delta: Float) {
        pollInputForUI() //todo: maybe extract it to separate input handler and add it to input multiplexer & send events through event system?
        stage.update(delta)
        ecsWorld.setDelta(delta).apply { ecsWorld.process() }
        RemovalService.process()
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)

    private fun setupUI() {
        val screenWidth = Gdx.graphics.widthF()
        val screenHeight = Gdx.graphics.heightF()
        stage.addActor(inventoryWindow)
        inventoryWindow.setSize(screenWidth - 250f, screenHeight - 200f)
        inventoryWindow.setPosition(
                Gdx.graphics.widthF() / 2 - inventoryWindow.width / 2,
                Gdx.graphics.heightF() / 2 - inventoryWindow.height / 2)
        inventoryWindow.isVisible = false

        stage.addActor(moneyDisplay)
        moneyDisplay.setPosition(25f, Gdx.graphics.heightF() - 25f)
        gamePadUIHandler.addToggleableActor(6, inventoryWindow)//this is hardcoded for now
        keyboardUIHandler.addToggleableActor(Input.Keys.I, inventoryWindow)
        //todo: think about how to pass mapping/mapping button code into this
    }

    private fun pollInputForUI() {
        val mapping = gamePadHandler.mapping
        val controller = gamePadHandler.currentController

        updateInventorySelection(controller, mapping)

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            moneyDisplay.startCountdown(1500)
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val targetX = Gdx.input.x.toFloat()
            var targetY = Gdx.input.y.toFloat()
            val angle = testTransform.position.rotationAngleTo(targetX, targetY)
            testTransform.rotationAngle = angle
        }
    }

    //todo: move this clusterfuck to separate event-based handlers because my god this looks bad
    private fun updateInventorySelection(controller: Controller?, mapping: ControllerMapping?) {
        if (inventoryWindow.isVisible) {
            //todo: move this to gamepad ui handler
            if (mapping != null && controller != null) {
                if (controller.getButton(mapping.buttonDpadLeft)) {
                    inventoryWindow.moveLeft()
                }

                if (controller.getButton(mapping.buttonDpadRight)) {
                    inventoryWindow.moveRight()
                }

                if (controller.getButton(mapping.buttonA)) {
                    println("elo")
                    inventoryWindow.currentSlot().simulateClick()
                }
            } else {
                //todo: move this to keyboard ui handler
                if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    inventoryWindow.moveLeft()
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    inventoryWindow.moveRight()
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                    inventoryWindow.moveUp()
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                    inventoryWindow.moveDown()
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    inventoryWindow.currentSlot().simulateClick()
                }
            }
        }
    }
}