package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.Actor

class QuickItemAccessMenu : Actor() {
    //todo: add an array of 4 slots here
    //todo: add keybinds (make this an instance of UI Controller?)
    //todo: display slots in a cross pattern (like a dpad on controller) and display keybinds next to items
    //todo: allow binding inventory slots to quick access slots
    private val slots = Array(4) { QuickAccessSlot() }
}
