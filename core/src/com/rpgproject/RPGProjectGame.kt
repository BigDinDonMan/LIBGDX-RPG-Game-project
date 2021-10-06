package com.rpgproject

import com.artemis.WorldConfigurationBuilder
import com.artemis.io.JsonArtemisSerializer
import com.artemis.managers.WorldSerializationManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.rpgproject.ecs.systems.CollisionHandlingSystem
import com.rpgproject.ecs.systems.PhysicsSystem
import com.rpgproject.ecs.systems.RenderSystem
import com.rpgproject.screens.GameScreen
import com.rpgproject.screens.MainMenuScreen
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
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
        Instance = this
        assetManager = AssetManager().apply { load("Jester.png", Texture::class.java) }.apply { finishLoading() }
        batch = SpriteBatch()
        mainCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        physicsWorld = PhysicsWorld(Vector2(0f, -10f), false)
        initArtemis()
        physicsWorld.setContactListener(CollisionListener(eventBus))


        addScreen(MainMenuScreen())
        addScreen(GameScreen(ecsWorld))
        setScreen<MainMenuScreen>()
    }

    override fun render() {
        clearScreen(0f, 0f, 0f)
        mainCamera.update()
        ecsWorld.process()
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
    }

    private fun initArtemis() {
        serializationManager = WorldSerializationManager()
        val config = WorldConfigurationBuilder()
                .with(
                        PhysicsSystem(physicsWorld, 6, 2),
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