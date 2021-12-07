package com.rpgproject.ui

interface IFocusable {
    fun handleKeyboardKey(keyCode: Int)
    fun handleGamePadButton(buttonCode: Int)
}