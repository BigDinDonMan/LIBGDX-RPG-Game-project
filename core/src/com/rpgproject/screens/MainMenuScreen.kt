package com.rpgproject.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.StretchViewport
import ktx.app.KtxScreen
import kotlin.system.exitProcess

//add viewport to handle resizing
//add camera to the constructor
class MainMenuScreen(val assetManager: AssetManager) : KtxScreen {

    private val viewport = StretchViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val stage = Stage(viewport)
    private val menuGroups = ArrayList<Group>()

    init {
        initUI()
    }

    private fun initUI() {
        //title, start game button, load game button (switches to different view), options (switches to different view), exit game
        //start game should also switch to different view that lets you name the player
        val skin = Skin(Gdx.files.internal("skins/uiskin.json"))
        val menuGroup = Group()
        val enterNameGroup = Group()
        val optionsGroup = Group()
        val loadGameGroup = Group()
        val menuGroupItems = arrayOf<Actor>(
            Label("Mythos Hunter", skin),
            ImageTextButton("New game", skin).apply {
                addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                    }
                })
            },
            ImageTextButton("Load game", skin).apply {
                addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                    }
                })
            },
            ImageTextButton("Options", skin).apply {
                addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                    }
                })
            },
            ImageTextButton("Exit", skin).apply {
                addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                        Gdx.app.exit()
                        exitProcess(0)
                    }
                })
            } //temporary skin
        )
        menuGroupItems.forEach(menuGroup::addActor)

        stage.addActor(menuGroup)
        stage.addActor(enterNameGroup)
        stage.addActor(optionsGroup)
        stage.addActor(loadGameGroup)
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)

    override fun render(delta: Float) {
        stage.act()
        stage.draw()
    }
}
