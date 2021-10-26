package com.rpgproject.input

import com.artemis.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.rpgproject.ecs.events.specific.PlayerInputEvent
import ktx.app.KtxInputAdapter
import net.mostlyoriginal.api.event.common.EventSystem

class KeyboardHandler(val playerEntity: Entity, val eventSystem: EventSystem) : KtxInputAdapter {

    private var x = 0f
    private var y = 0f

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> y = 1f
            Input.Keys.S -> y = -1f
            Input.Keys.A -> x = -1f
            Input.Keys.D -> x = 1f
        }

        eventSystem.dispatch(PlayerInputEvent(playerEntity, x, y))

        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> y = if (Gdx.input.isKeyPressed(Input.Keys.S)) y else 0f
            Input.Keys.S -> y = if (Gdx.input.isKeyPressed(Input.Keys.W)) y else 0f
            Input.Keys.A -> x = if (Gdx.input.isKeyPressed(Input.Keys.D)) x else 0f
            Input.Keys.D -> x = if (Gdx.input.isKeyPressed(Input.Keys.A)) x else 0f
        }

        eventSystem.dispatch(PlayerInputEvent(playerEntity, x, y))

        return true
    }
}