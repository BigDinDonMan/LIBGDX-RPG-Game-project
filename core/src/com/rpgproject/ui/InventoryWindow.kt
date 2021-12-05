package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.rpgproject.inventory.Inventory

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window {

    val slots = ArrayList<ImageButton>()
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
        add(slotsTable).align(Align.bottomLeft).expand()
        slotsTable.setFillParent(true)
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
    }

    private fun setUpInventoryListeners() {
        //todo: setup onItemAdded and onItemRemoved and subscribe to them here, to make them update the UI when new items are added or removed
    }
}