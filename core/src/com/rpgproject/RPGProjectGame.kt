package com.rpgproject

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.levelling.LevelDataComponent
import com.rpgproject.util.Mappers
import ktx.app.KtxGame
import ktx.app.clearScreen
import ktx.app.emptyScreen
import kotlin.system.exitProcess

class RPGProjectGame : KtxGame<Screen>() {

    private lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        registerMappers()
    }

    override fun render() {
        clearScreen(0f, 0f, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
            exitProcess(0)
        }
    }

    override fun dispose() {
        batch.dispose()
    }

    private fun registerMappers() {
        Mappers.registerFor(PlayerComponent::class.java)
        Mappers.registerFor(LevelDataComponent::class.java)
    }
}