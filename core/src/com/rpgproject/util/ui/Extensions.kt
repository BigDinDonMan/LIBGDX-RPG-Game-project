package com.rpgproject.util.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Pools

fun Stage.update(delta: Float) = this.act(delta).also { this.draw() }

fun Actor.simulateClick() {
    val event = Pools.obtain(InputEvent::class.java).apply {
        reset()
        relatedActor = this@simulateClick //this binds the actor calling this function
        button = Input.Buttons.LEFT //simulate left click, here we pass index of left mouse button (0 by default)
        type = InputEvent.Type.touchDown
    }

    this.fire(event)

    event.type = InputEvent.Type.touchUp
    this.fire(event)

    Pools.free(event)
}