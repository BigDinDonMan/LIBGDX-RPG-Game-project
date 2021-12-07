package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ObjectMap

interface UIActorToggleable {

    val stage: Stage
    val toggleable: ObjectMap<Int, Actor>

    fun addToggleableActor(code: Int, toggleable: Actor) = this.toggleable.put(code, toggleable)

    fun getCurrentlyFocusedActor(): UIController? = stage.actors.filterIsInstance<UIController>().first { (it as Actor).isVisible }

    //code is either gamepad button code or key code
    fun handleToggle(code: Int) {
        if (toggleable.containsKey(code)) {
            toggleable[code].isVisible = !toggleable[code].isVisible
        }
    }
}
