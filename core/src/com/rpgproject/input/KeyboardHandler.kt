package com.rpgproject.input

import ktx.app.KtxInputAdapter

class KeyboardHandler : KtxInputAdapter {


    override fun keyDown(keycode: Int): Boolean {
        return true
    }


}