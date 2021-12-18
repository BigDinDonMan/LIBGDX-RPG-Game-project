package com.rpgproject.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.rpgproject.inventory.Inventory
import com.rpgproject.util.ui.simulateClick

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window, UIController {

    //todo: clear selection on closing the window
    val slots = ArrayList<InventorySlot>()
    private val slotsTable = Table()
    private var slotsPerRow = 8
    private var currentSlotIndex = 0
    private val defaultColor = Color(51f,51f,51f,255f)
    private val selectionColor = Color.YELLOW

    constructor(title: String, style: WindowStyle) : super(title, style) {
        initUI()
    }

    constructor(title: String, skin: Skin) : super(title, skin) {
        initUI()
    }

    private fun initUI() {
        val rows = Inventory.items.size / slotsPerRow
        this.align(Align.bottomLeft)
        add(slotsTable).bottom().left()
        var index = 0
        for (i in 0 until rows) {
            slotsTable.row().grow()
            for (j in 0 until slotsPerRow) {
                val slot = InventorySlot(skin, index++)
                slots += slot
                slotsTable.add(slot).prefSize(75f, 75f).pad(5f)
            }
        }
        slots[0].color = selectionColor
    }

    private fun onSelectionChanged(oldIndex: Int, newIndex: Int) {
        val oldSlot = slots[oldIndex]
        val currentSlot = slots[newIndex]

        oldSlot.color = defaultColor
        currentSlot.color = selectionColor
    }

    fun currentSlot(): InventorySlot = this.slots[currentSlotIndex]

    fun moveRight() {
        val oldIndex = currentSlotIndex
        currentSlotIndex++
        if (currentSlotIndex >= Inventory.items.size) {
            currentSlotIndex = 0
        }
        onSelectionChanged(oldIndex, currentSlotIndex)
    }

    fun moveLeft() {
        val oldIndex = currentSlotIndex
        currentSlotIndex--
        if (currentSlotIndex < 0) {
            currentSlotIndex = Inventory.items.size - 1
        }
        onSelectionChanged(oldIndex, currentSlotIndex)
    }

    fun moveUp() {
        val oldIndex = currentSlotIndex
        currentSlotIndex -= slotsPerRow
        if (currentSlotIndex < 0) {
            currentSlotIndex += Inventory.items.size
        }
        onSelectionChanged(oldIndex, currentSlotIndex)
    }

    fun moveDown() {
        val oldIndex = currentSlotIndex
        currentSlotIndex += slotsPerRow
        currentSlotIndex %= Inventory.items.size
        onSelectionChanged(oldIndex, currentSlotIndex)
    }

    override fun handleKeyboardKey(keyCode: Int): Boolean {
        if (!isVisible) return false

        var handled = true

        when (keyCode) {
            Input.Keys.LEFT -> moveLeft()
            Input.Keys.RIGHT -> moveRight()
            Input.Keys.UP -> moveUp()
            Input.Keys.DOWN -> moveDown()
            Input.Keys.ENTER -> currentSlot().simulateClick()
            else -> handled = false
        }

        return handled
    }

    override fun handleGamePadButton(controller: Controller?, buttonCode: Int): Boolean {
        val mapping = controller?.mapping ?: return false
        if (!isVisible) return false

        var handled = true

        when (buttonCode) {
            mapping.buttonDpadLeft -> moveLeft()
            mapping.buttonDpadRight -> moveRight()
            mapping.buttonDpadDown -> moveDown()
            mapping.buttonDpadUp -> moveUp()
            mapping.buttonA -> currentSlot().simulateClick()
            else -> handled = false
        }

        return handled
    }
}
