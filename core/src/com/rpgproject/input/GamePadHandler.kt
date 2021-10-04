package com.rpgproject.input

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerAdapter
import ktx.app.KtxInputAdapter

class GamePadHandler : ControllerAdapter() {

    override fun connected(controller: Controller?) {
        super.connected(controller)
    }

    override fun disconnected(controller: Controller?) {
        super.disconnected(controller)
    }

    override fun buttonDown(controller: Controller?, buttonIndex: Int): Boolean {
        return super.buttonDown(controller, buttonIndex)
    }
}