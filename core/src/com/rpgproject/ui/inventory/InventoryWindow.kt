package com.rpgproject.ui.inventory

import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.rpgproject.inventory.Inventory
import com.rpgproject.ui.controllers.UIController
import com.rpgproject.util.ui.simulateClick

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window, UIController {

    //todo: clear selection on closing the window
    //todo: add mouseOver and mouseExit event listeners that will select slot on mouse over and de-select it (maybe?) on mouse exit
    val slots = ArrayList<InventorySlot>()
    private val slotsTable = Table()
    private var slotsPerRow = 8
    private var currentSlotIndex = 0
    private val defaultColor = Color(51f,51f,51f,255f)
    private val selectionColor = Color.YELLOW

    constructor(title: String, style: WindowStyle, currencyIcon: Texture) : super(title, style) {
        initUI(currencyIcon)
    }

    constructor(title: String, skin: Skin, currencyIcon: Texture) : super(title, skin) {
        initUI(currencyIcon)
    }

    private fun initUI(currencyIcon: Texture) {
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
//        val currencyDisplay = CurrencyDisplay(currencyIcon)
//        Inventory.onCurrencyChanged += currencyDisplay::update
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

    //this is a temporary function to check whether it correctly removes items from inventory
    fun dropItem() {
        val itemData = Inventory.itemAt(currentSlot().inventoryIndex)
        Inventory.removeItem(itemData.item!!, 5)
        slots.forEach { it.updateDisplay() }
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
            Input.Keys.R -> dropItem()
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
