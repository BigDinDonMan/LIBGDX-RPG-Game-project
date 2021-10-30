package com.rpgproject

import com.artemis.WorldConfigurationBuilder
import com.artemis.io.JsonArtemisSerializer
import com.artemis.managers.WorldSerializationManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2D
import com.rpgproject.ecs.systems.*
import com.rpgproject.screens.GameScreen
import com.rpgproject.screens.MainMenuScreen
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import com.rpgproject.util.assets.ShaderStorage
import com.rpgproject.util.physics.CollisionListener
import ktx.app.KtxGame
import ktx.app.clearScreen
import net.mostlyoriginal.api.event.common.EventSystem
import kotlin.system.exitProcess

class RPGProjectGame : KtxGame<Screen>() {

    private lateinit var batch: SpriteBatch
    private lateinit var ecsWorld: EcsWorld
    private lateinit var physicsWorld: PhysicsWorld
    private lateinit var mainCamera: OrthographicCamera
    private lateinit var serializationManager: WorldSerializationManager
    private lateinit var assetManager: AssetManager
    private lateinit var eventBus: EventSystem

    companion object {
        lateinit var Instance: RPGProjectGame
    }

    override fun create() {
        Box2D.init()
        Instance = this
        assetManager = AssetManager()
        batch = SpriteBatch()
        mainCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        physicsWorld = PhysicsWorld(Vector2(0f, -10f), false)
        initArtemis()
        physicsWorld.setContactListener(CollisionListener(eventBus))


        addScreen(MainMenuScreen(mainCamera))
        addScreen(GameScreen(ecsWorld, physicsWorld, eventBus, mainCamera))
        setScreen<GameScreen>()
    }

    override fun render() {
        clearScreen(1f, 1f, 1f)
        mainCamera.update()
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
            exitProcess(0)
        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            println("henlo")
//            val e = ecsWorld.createEntity()
//            ecsWorld.edit(e.id).add(TransformComponent()).add(TextureComponent().apply { texture=assetManager.get("Jester.png", Texture::class.java) })
//        }
        currentScreen.render(Gdx.graphics.deltaTime)
    }

    override fun dispose() {
        batch.dispose()
        ecsWorld.dispose()
        physicsWorld.dispose()
        assetManager.dispose()
        ShaderStorage.dispose()
    }

    private fun initArtemis() {
        serializationManager = WorldSerializationManager()
        val config = WorldConfigurationBuilder()
                .with(
                        PlayerInputSystem(),
                        CameraMovementSystem(mainCamera, 0.6f),
                        PhysicsSystem(physicsWorld, 6, 2),
                        PhysicsDebugSystem(physicsWorld, mainCamera),
                        InteractionSystem(),
                        RenderSystem(batch, mainCamera),
                        CollisionHandlingSystem())
                .build()
        eventBus = EventSystem()
        config.setSystem(eventBus)
        config.setSystem(serializationManager)
        ecsWorld = EcsWorld(config)
        serializationManager.setSerializer(JsonArtemisSerializer(ecsWorld))
    }
}