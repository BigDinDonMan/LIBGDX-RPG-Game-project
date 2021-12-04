package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.rpgproject.inventory.Inventory

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window {

    val slots = ArrayList<InventorySlot>()
    private val slotsTable = Table()

    constructor(title: String, style: WindowStyle) : super(title, style) {
        initUI()
    }

    constructor(title: String, skin: Skin) : super(title, skin) {
        initUI()
    }

    private fun initUI() {
        val slotsPerRow = 6 //might be subject to change
        val rows = Inventory.items.size / slotsPerRow
        add(slotsTable).left().bottom().expand()
        slotsTable.setFillParent(true)
        slotsTable.debug()
        for (i in 0 until rows) {
            slotsTable.row().expandX()
            for (j in 0 until slotsPerRow) {
                val slot = InventorySlot(skin)
                slots += slot
                slotsTable.add(slot).width(50f).height(50f).padLeft(5f).padRight(5f).expand(true, true)
            }
        }
    }

    private fun setUpInventoryListeners() {
        //todo: setup onItemAdded and onItemRemoved and subscribe to them here, to make them update the UI when new items are added or removed
    }
}