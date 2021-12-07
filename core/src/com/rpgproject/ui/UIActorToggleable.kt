package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ObjectMap

interface UIActorToggleable {

    val stage: Stage
    val toggleables: ObjectMap<Int, Actor>

    fun addToggleableActor(code: Int, toggleable: Actor) = this.toggleables.put(code, toggleable)

    fun getCurrentlyFocusedActor(): UIController? = stage.actors.filterIsInstance<UIController>().first { (it as Actor).isVisible }

    //code is either gamepad button code or key code
    fun handleToggle(code: Int):Boolean {
        return if (toggleables.containsKey(code)) {
            toggleables[code].isVisible = !toggleables[code].isVisible
            for (key in toggleables.keys()) {
                if (key != code) {
                    toggleables[key].isVisible = false
                }
            }
            true
        } else false
    }
}
