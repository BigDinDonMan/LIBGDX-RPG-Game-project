package com.rpgproject.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.rpgproject.inventory.Inventory

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window {

    val slots = ArrayList<InventorySlot>()
    private val slotsTable = Table()
    private var slotsPerRow = 6
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
        add(slotsTable).align(Align.bottomLeft).expand()
        slotsTable.debug()
        var index = 0
        for (i in 0 until rows) {
            slotsTable.row().expandX()
            for (j in 0 until slotsPerRow) {
                val slot = InventorySlot(skin, index++)
                slots += slot
                slotsTable.add(slot).width(75f).height(75f).pad(5f)
            }
        }
        slots[0].setColor(selectionColor)
    }

    private fun setUpInventoryListeners() {
        //todo: setup onItemAdded and onItemRemoved and subscribe to them here, to make them update the UI when new items are added or removed
    }

    private fun onSelectionChanged(oldIndex: Int, newIndex: Int) {
        val oldSlot = slots[oldIndex]
        val currentSlot = slots[newIndex]

        oldSlot.setColor(defaultColor)
        currentSlot.setColor(selectionColor)
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
}