package com.rpgproject.input

import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.ObjectMap
import com.rpgproject.ui.IFocusable

//todo: add a map of "togglable" actors with button code as a key and actor as a value
//same in keyboard ui handler
//todo: add handleKey and handleButton methods in IFocusable (or add another interface) with signature like fun(keyCode: Input.Keys): Unit (or boolean?) and fun (buttonCode: Int)
class GamePadUIHandler : ControllerAdapter(), IFocusable {
    private val togglables = ObjectMap<Int, Actor>()

    fun addTogglableActor(buttonCode: Int, actor: Actor) {
        togglables.put(buttonCode, actor)
    }

    override fun handleKeyboardKey(keyCode: Int) {
        TODO("Not yet implemented")
    }

    override fun handleGamePadButton(buttonCode: Int) {
        TODO("Not yet implemented")
    }
}