package com.rpgproject.input

import com.badlogic.gdx.controllers.ControllerAdapter

//todo: add a map of "togglable" actors with button code as a key and actor as a value
//same in keyboard ui handler
//todo: add handleKey and handleButton methods in IFocusable (or add another interface) with signature like fun(keyCode: Input.Keys): Unit (or boolean?) and fun (buttonCode: Int)
class GamePadUIHandler : ControllerAdapter() {
}