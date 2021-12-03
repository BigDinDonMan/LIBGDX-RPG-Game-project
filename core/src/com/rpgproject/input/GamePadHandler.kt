package com.rpgproject.input

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.controllers.Controllers
import com.rpgproject.util.input.ControllerMappings
import net.mostlyoriginal.api.event.common.EventSystem

class GamePadHandler(val eventSystem: EventSystem) : ControllerAdapter() {

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
        println("connected")
    }

    override fun disconnected(controller: Controller?) {
        currentController = null //just dont care which is disconnected because thats a single player game
        println("disconnected")
    }

    override fun buttonDown(controller: Controller?, buttonIndex: Int): Boolean {
        println(buttonIndex)
        return super.buttonDown(controller, buttonIndex)
    }

    override fun axisMoved(controller: Controller?, axisIndex: Int, value: Float): Boolean {
//        val currentX = 0f
//        val currentY = 0f
//        return if (axisIndex == ControllerMappings.X_AXIS) {
//            xAxisInput = value
//            true;
//        } else if (axisIndex == ControllerMappings.Y_AXIS) {
//            yAxisInput = value
//            true;
//        } else false
        return true
    }
}