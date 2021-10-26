package com.rpgproject.input

import com.artemis.Entity
import com.badlogic.gdx.Input
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import ktx.app.KtxInputAdapter
import net.mostlyoriginal.api.event.common.EventSystem

class KeyboardHandler(val playerEntity: Entity, val eventSystem: EventSystem) : KtxInputAdapter {

    private var x = 0f
    private var y = 0f

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) { //todo: handle the case when you quickly stop pressing the key and quickly press the one with opposite value
            Input.Keys.W -> y = 1f
            Input.Keys.S -> y = -1f
            Input.Keys.A -> x = -1f
            Input.Keys.D -> x = 1f
        }

        eventSystem.dispatch(PlayerInputEvent(playerEntity, x, y))

        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) { //todo: dont set params to 0 when the other key is pressed
            Input.Keys.W -> y = 0f
            Input.Keys.S -> y = 0f
            Input.Keys.A -> x = 0f
            Input.Keys.D -> x = 0f
        }

        eventSystem.dispatch(PlayerInputEvent(playerEntity, x, y))

        return true
    }
}