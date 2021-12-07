package com.rpgproject.ui

import com.badlogic.gdx.controllers.Controller

interface UIController {
    fun handleKeyboardKey(keyCode: Int): Boolean
    fun handleGamePadButton(controller: Controller?, buttonCode: Int): Boolean
}