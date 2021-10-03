package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.rpgproject.inventory.InventoryItem
import ktx.scene2d.*

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window {

    constructor(title: String, style: WindowStyle) : super(title, style) {}

    constructor(title: String, skin: Skin) : super(title, skin) {}

    private fun initUI() {

    }

    private fun setUpInventoryListeners() {

    }

    private fun tryAddItem(item: InventoryItem): Boolean = false
    private fun tryRemoveItem(item: InventoryItem): Boolean = false
}