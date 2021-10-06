package com.rpgproject.screens

import com.rpgproject.util.EcsWorld
import com.rpgproject.util.PhysicsWorld
import ktx.app.KtxScreen

class GameScreen(private val ecsWorld: EcsWorld, private val physicsWorld: PhysicsWorld) : KtxScreen {

    override fun dispose() {
    }

    override fun render(delta: Float) {
        ecsWorld.setDelta(delta).apply { ecsWorld.process() }
    }

}