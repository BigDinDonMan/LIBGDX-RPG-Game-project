package com.rpgproject.screens

import com.rpgproject.util.EcsWorld
import ktx.app.KtxScreen

class GameScreen(private val ecsWorld: EcsWorld) : KtxScreen {

    override fun dispose() {
    }

    override fun render(delta: Float) {
        ecsWorld.setDelta(delta).apply { ecsWorld.process() }
    }

}