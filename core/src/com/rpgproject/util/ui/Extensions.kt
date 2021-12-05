package com.rpgproject.util.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Pools

fun Stage.update(delta: Float) = this.act(delta).also { this.draw() }

fun Actor.simulateClick() {
    val event = Pools.obtain(InputEvent::class.java).apply {
        reset()
        type = InputEvent.Type.touchDown
    }

    this.fire(event)

    event.type = InputEvent.Type.touchUp
    this.fire(event)

    Pools.free(event)
}