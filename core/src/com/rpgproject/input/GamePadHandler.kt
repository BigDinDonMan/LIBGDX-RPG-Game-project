package com.rpgproject.input

import com.artemis.Entity
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.controllers.Controllers
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import com.rpgproject.util.input.ControllerInputMappings
import net.mostlyoriginal.api.event.common.EventSystem

//todo: set this as an abstract class
//handle Xbox360/XOne/Ps4 controllers separately
class GamePadHandler(val eventSystem: EventSystem) : ControllerAdapter() {

    private var playerEntity: Entity? = null

    private var xAxisInput = 0f
    private var yAxisInput = 0f

    private var currentController: Controller? = null

    init {
        Controllers.addListener(this)
        setUpController(Controllers.getCurrent())
    }

    private fun setUpController(controller: Controller?) {
        if (controller != null) {
            controller.addListener(this)
            currentController = controller
        }
    }

    override fun connected(controller: Controller?) {
        setUpController(controller)
    }

    override fun disconnected(controller: Controller?) {
        currentController = null //just dont care which is disconnected because thats a single player game
    }

    override fun buttonDown(controller: Controller?, buttonIndex: Int): Boolean {
        println(buttonIndex)
        return super.buttonDown(controller, buttonIndex)
    }

    override fun axisMoved(controller: Controller?, axisIndex: Int, value: Float): Boolean {
        if (axisIndex == ControllerInputMappings.X_AXIS) {
            xAxisInput = value
        }
        if (axisIndex == ControllerInputMappings.Y_AXIS) {
            yAxisInput = -value //for some reason, y axis goes into negatives if you go upwards
        }
        //todo: tbh, I think its time to split this event into separate ones (move direction event, attack event, etc., or change it into keyboard and gamepad events)
        eventSystem.dispatch(PlayerInputEvent(playerEntity!!, xAxisInput, yAxisInput, false, false, false))
        return true
    }

    fun injectPlayerEntity(e: Entity) {
        this.playerEntity = e
    }
}