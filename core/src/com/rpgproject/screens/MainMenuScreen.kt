package com.rpgproject.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.viewport.StretchViewport
import ktx.app.KtxScreen

//add viewport to handle resizing
//add camera to the constructor
class MainMenuScreen(val camera: Camera) : KtxScreen {

    private val viewport = StretchViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)

    override fun resize(width: Int, height: Int) = viewport.update(width, height)

}