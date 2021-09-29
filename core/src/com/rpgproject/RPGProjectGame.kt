package com.rpgproject

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.rpgproject.ecs.components.PlayerComponent
import com.rpgproject.ecs.components.levelling.LevelDataComponent
import com.rpgproject.util.Mappers
import kotlin.system.exitProcess

class RPGProjectGame : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        Mappers.registerFor(PlayerComponent::class.java)
        Mappers.registerFor(LevelDataComponent::class.java)
    }

    override fun render() {
        ScreenUtils.clear(Color.BLACK)
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
            exitProcess(0)
        }
    }

    override fun dispose() {
        batch.dispose()
    }
}