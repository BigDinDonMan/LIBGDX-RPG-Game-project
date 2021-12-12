package com.rpgproject.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import ktx.app.KtxScreen

class LoadingScreen(private val assetManager: AssetManager) : KtxScreen {

    override fun render(delta: Float) {
        if (assetManager.update()) {
            //todo: swap the screen
        } else {
            val progress = assetManager.getProgress()
        }
    }

    //todo: rename this function
    fun updateAssets(transitionTo: Screen, vararg assetPaths: String) {

    }
}
