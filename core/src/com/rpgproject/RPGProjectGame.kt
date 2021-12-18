package com.rpgproject

import com.artemis.WorldConfigurationBuilder
import com.artemis.io.JsonArtemisSerializer
import com.artemis.managers.WorldSerializationManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2D
import com.rpgproject.ecs.systems.*
import com.rpgproject.screens.GameScreen
import com.rpgproject.screens.LoadingScreen
import com.rpgproject.screens.MainMenuScreen
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import com.rpgproject.util.assets.ShaderStorage
import com.rpgproject.util.ecs.RemovalService
import com.rpgproject.util.physics.CollisionListener
import ktx.app.KtxGame
import ktx.app.clearScreen
import net.mostlyoriginal.api.event.common.EventSystem
import kotlin.system.exitProcess

//todo: if implementing skill effect system: add a map with cached functions for each skill
//todo: each skill should have its corresponding static method in a class that will be fetched using reflection
// and then cached in that map
//todo: ui gamepad handler will probably have to be instantiated here to allow starting the game using a game controller
//todo: add ItemInformationDisplay Actor that will be updated on inventory slot selection
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
        initTtfFontSupport()
        batch = SpriteBatch()
        mainCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        physicsWorld = PhysicsWorld(Vector2(0f, -10f), false)
        initArtemis()
        physicsWorld.setContactListener(CollisionListener(eventBus))

        RemovalService.init(ecsWorld, physicsWorld)

        loadI18nBundles()

        addScreen(LoadingScreen(assetManager))
        addScreen(MainMenuScreen(mainCamera))
        addScreen(GameScreen(ecsWorld, physicsWorld, eventBus, mainCamera))
        setScreen<GameScreen>() //temporarily, for testing purposes
    }

    override fun render() {
        clearScreen(0f, 0f, 0f)
        mainCamera.update()
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
            exitProcess(0)
        }
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
                        CameraMovementSystem(mainCamera, 0.3f),
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

    private fun loadI18nBundles() {

    }

    private fun initTtfFontSupport() {
        val resolver = InternalFileHandleResolver()
        assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        assetManager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
    }
}
