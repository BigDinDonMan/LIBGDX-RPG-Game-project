package com.rpgproject.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.rpgproject.ecs.components.*
import com.rpgproject.ecs.systems.InteractionSystem
import com.rpgproject.input.GamePadHandler
import com.rpgproject.input.GamePadUIHandler
import com.rpgproject.input.KeyboardHandler
import com.rpgproject.input.KeyboardUIHandler
import com.rpgproject.inventory.Inventory
import com.rpgproject.inventory.InventoryItem
import com.rpgproject.ui.AnimatedCountdownLabel
import com.rpgproject.ui.InventoryWindow
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import com.rpgproject.util.ecs.RemovalService
import com.rpgproject.util.heightF
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
    private val potionTexture = Texture(Gdx.files.internal("potion.jpg"))
    private val stickTexture = Texture(Gdx.files.internal("stick.png"))
    private var testEntity by Delegates.notNull<Int>()
    private val testTransform = TransformComponent();
    private var player by Delegates.notNull<Int>()
    lateinit var playerTransform: TransformComponent
    val testItem =InventoryItem("Health potion", "Heals 100 HP", potionTexture, 3)

    init {
        setupUI()
        spawnTestEntities()
    }

    private fun spawnTestEntities() {
        val playerEntity = ecsWorld.create()
        player = playerEntity
        ecsWorld.getSystem(InteractionSystem::class.java).injectPlayerEntity(ecsWorld.getEntity(playerEntity))
        val transform = TransformComponent()
        playerTransform = transform
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

        val potionEntity = ecsWorld.create()
        val stickEntity = ecsWorld.create()
        val potionCount = 14
        ecsWorld.edit(potionEntity).add(TransformComponent().apply {
            position.set(-50f, -50f, -50f)
            size.set(potionTexture.width.toFloat(), potionTexture.height.toFloat(), 0f)
        }).
        add(TextureComponent().apply { texture = potionTexture }).
        add(InteractableComponent().apply { interactableType = InteractableComponent.InteractableObjectType.PICKUP }).
        add(InventoryItemComponent().apply {
            item = testItem
            amount = potionCount
        })
        val anotherPotionEntity = ecsWorld.create()
        ecsWorld.edit(anotherPotionEntity).add(TransformComponent().apply {
            position.set(-150f, -150f, -150f)
            size.set(potionTexture.width.toFloat(), potionTexture.height.toFloat(), 0f)
        }).
        add(TextureComponent().apply { texture = potionTexture }).
        add(InteractableComponent().apply { interactableType = InteractableComponent.InteractableObjectType.PICKUP }).
        add(InventoryItemComponent().apply {
            amount = potionCount
            item = testItem
        })
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun render(delta: Float) {
        stage.update(delta)
        ecsWorld.setDelta(delta).apply { ecsWorld.process() }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Inventory.removeItem(testItem, 7)
        }
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

        //todo: add listener functions here that update specific index in inventory
        Inventory.onItemAdded += { _, position, _ -> inventoryWindow.slots[position].updateDisplay() }
        Inventory.onItemRemoved += { _, position, _ -> inventoryWindow.slots[position].updateDisplay() }

        stage.addActor(moneyDisplay)
        moneyDisplay.setPosition(25f, Gdx.graphics.heightF() - 25f)
        gamePadUIHandler.addToggleableActor(6, inventoryWindow)//this is hardcoded for now
        keyboardUIHandler.addToggleableActor(Input.Keys.I, inventoryWindow)
    }
}
