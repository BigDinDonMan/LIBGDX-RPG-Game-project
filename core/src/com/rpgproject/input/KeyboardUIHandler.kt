package com.rpgproject.input

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ObjectMap
import com.rpgproject.ui.UIActorToggleable
import ktx.app.KtxInputAdapter

class KeyboardUIHandler(override val stage: Stage) : KtxInputAdapter, UIActorToggleable {
    override val toggleables: ObjectMap<Int, Actor> = ObjectMap()

    override fun keyDown(keycode: Int): Boolean {
        handleToggle(keycode)
        return getControlledActors().any { a -> a.handleKeyboardKey(keycode) }
    }
}