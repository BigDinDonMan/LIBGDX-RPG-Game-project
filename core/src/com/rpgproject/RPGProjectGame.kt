package com.rpgproject

import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.rpgproject.ecs.systems.RenderSystem
import com.rpgproject.screens.MainMenuScreen
import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import ktx.app.KtxGame
import ktx.app.clearScreen
import net.mostlyoriginal.api.event.common.EventSystem
import kotlin.system.exitProcess

class RPGProjectGame : KtxGame<Screen>() {

    private lateinit var batch: SpriteBatch
    private lateinit var ecsWorld: EcsWorld
    private lateinit var physicsWorld: PhysicsWorld
    private lateinit var mainCamera: OrthographicCamera

    override fun create() {
        batch = SpriteBatch()
        mainCamera = OrthographicCamera()
        val config = WorldConfigurationBuilder()
                .with(RenderSystem(batch, mainCamera))
                .build()
        config.setSystem(EventSystem::class.java)

        ecsWorld = EcsWorld(config)
        physicsWorld = PhysicsWorld(Vector2(0f, -10f), false)

        addScreen(MainMenuScreen())
        setScreen<MainMenuScreen>()
    }

    override fun render() {
        clearScreen(0f, 0f, 0f)
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
    }
}