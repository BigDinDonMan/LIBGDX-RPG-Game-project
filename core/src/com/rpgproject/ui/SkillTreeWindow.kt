package com.rpgproject.ui

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.rpgproject.ui.controllers.UIController

class SkillTreeWindow : Window, UIController {
    constructor(title: String, style: WindowStyle) : super(title, style) {
    }

    constructor(title: String, skin: Skin) : super(title, skin) {
    }

    override fun handleKeyboardKey(keyCode: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun handleGamePadButton(controller: Controller?, buttonCode: Int): Boolean {
        TODO("Not yet implemented")
    }
}
