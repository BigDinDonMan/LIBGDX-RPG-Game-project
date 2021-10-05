package com.rpgproject

import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rpgproject.screens.MainMenuScreen
import com.rpgproject.util.EcsWorld
import ktx.app.KtxGame
import ktx.app.clearScreen
import kotlin.system.exitProcess

class RPGProjectGame : KtxGame<Screen>() {

    private lateinit var batch: SpriteBatch
    private lateinit var ecsWorld: EcsWorld

    override fun create() {
        batch = SpriteBatch()
        val config = WorldConfigurationBuilder().build()

        ecsWorld = EcsWorld(config)

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
    }
}