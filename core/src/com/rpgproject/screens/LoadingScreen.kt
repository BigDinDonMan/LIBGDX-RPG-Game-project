package com.rpgproject.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import ktx.app.KtxScreen

class LoadingScreen(private val assetManager: AssetManager) : KtxScreen {

    private var transitionScreen: Screen? = null

    override fun render(delta: Float) {
        if (assetManager.update()) {
            //todo: swap the screen here
        } else {
            val progressPercentage = assetManager.getProgress() * 100
            val progressStr = progressPercentage.toInt().toString() + "%"
            //todo: render animations/whatever else here
            //todo: display progressStr in some sort of a label
        }
    }

    //todo: rename this function
    fun updateAssets(transitionTo: Screen, vararg assetData: AssetDescriptor<Any>) {
        transitionScreen = transitionTo
        assetData.forEach { data -> assetManager.load(data) }
    }
}
