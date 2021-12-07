package com.rpgproject.input

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ObjectMap
import com.rpgproject.ui.UIActorToggleable

//same in keyboard ui handler
//todo: add handleKey and handleButton methods in IFocusable (or add another interface) with signature like fun(keyCode: Input.Keys): Unit (or boolean?) and fun (buttonCode: Int)
//todo: unregister listener on object deletion
class GamePadUIHandler(override val stage: Stage) : ControllerAdapter(), UIActorToggleable {
    override val toggleables = ObjectMap<Int, Actor>() //todo: think about this...

    init {
        Controllers.addListener(this)
        Controllers.getCurrent()?.addListener(this)
    }

    override fun connected(controller: Controller?) {
        controller?.addListener(this)
    }

    override fun buttonDown(controller: Controller?, buttonIndex: Int): Boolean {
        handleToggle(buttonIndex)
        return getControlledActors().any { a -> a.handleGamePadButton(controller, buttonIndex) }
    }
}