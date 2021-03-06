package com.rpgproject.ui.inventory

import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.rpgproject.inventory.Inventory
import com.rpgproject.ui.controllers.UIController
import com.rpgproject.util.ui.simulateClick

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window, UIController {
    //todo: add a function to handle swapping/moving items in inventory
    //todo: clear selection on closing the window
    //todo: add mouseOver and mouseExit event listeners that will select slot on mouse over and de-select it (maybe?) on mouse exit
    val slots = ArrayList<InventorySlot>()
    private val slotsTable = Table()
    private var slotsPerRow = 8
    private var currentSlotIndex = 0
    private val defaultColor = Color(51f,51f,51f,255f)
    private val highlightColor = Color.YELLOW
    private val selectColor = Color.RED
    private var currentlySelected: InventorySlot? = null
    private var currentlyHighlighted: InventorySlot? = null

    constructor(title: String, style: WindowStyle, currencyIcon: Texture) : super(title, style) {
        initUI(currencyIcon)
    }

    constructor(title: String, skin: Skin, currencyIcon: Texture) : super(title, skin) {
        initUI(currencyIcon)
    }

    private fun initUI(currencyIcon: Texture) {
        val currencyDisplay = CurrencyDisplay(currencyIcon)
        Inventory.onCurrencyChanged += currencyDisplay::update
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
                slot.addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                        if (currentlySelected == null) {
                            if (slot.isEmpty()) return
                            currentlySelected = slot
                            slot.color = selectColor
                        } else {
                            if (slot === currentlySelected) {
                                currentlySelected = null
                                slot.color = defaultColor
                                return
                            }

                            val slotData1 = Inventory.itemAt(slot.inventoryIndex)
                            val slotData2 = Inventory.itemAt(currentlySelected!!.inventoryIndex)

                            if (slotData1.item == null && slotData2.item == null) {
                                currentlySelected?.color = defaultColor
                                currentlySelected = null
                            }

                            if (slotData1.item == slotData2.item) {
                                val currentAmount = slotData2.amount
                                val targetAmount = slotData1.amount
                                val maxStackSize = slotData1.item!!.maxStack
                                val sum = currentAmount + targetAmount
                                if (sum > maxStackSize) {
                                    slotData1.amount = maxStackSize
                                    slotData2.amount = sum - maxStackSize
                                } else {
                                    slotData1.amount = sum
                                    slotData2.clear()
                                }
                            } else {
                                val oldItem = slotData1.item
                                val oldAmount = slotData1.amount
                                slotData1.item = slotData2.item
                                slotData1.amount = slotData2.amount
                                slotData2.item = oldItem
                                slotData2.amount = oldAmount
                            }
                            slot.updateDisplay()
                            currentlySelected?.updateDisplay()
                            currentlySelected?.color = defaultColor
                            currentlySelected = null
                        }
                    }
                })
            }
        }
        slots[0].color = highlightColor
    }

    private fun onHighlightChanged(oldIndex: Int, newIndex: Int) {
        val oldSlot = slots[oldIndex]
        val currentSlot = slots[newIndex]

        oldSlot.color = if (oldSlot === currentlySelected) selectColor else defaultColor
        currentSlot.color = highlightColor
        currentlyHighlighted = currentSlot
    }

    private fun onSelectionChanged() {

    }

    fun currentSlot(): InventorySlot = this.slots[currentSlotIndex]

    fun moveRight() {
        val oldIndex = currentSlotIndex
        currentSlotIndex++
        if (currentSlotIndex >= Inventory.items.size) {
            currentSlotIndex = 0
        }
        onHighlightChanged(oldIndex, currentSlotIndex)
    }

    fun moveLeft() {
        val oldIndex = currentSlotIndex
        currentSlotIndex--
        if (currentSlotIndex < 0) {
            currentSlotIndex = Inventory.items.size - 1
        }
        onHighlightChanged(oldIndex, currentSlotIndex)
    }

    fun moveUp() {
        val oldIndex = currentSlotIndex
        currentSlotIndex -= slotsPerRow
        if (currentSlotIndex < 0) {
            currentSlotIndex += Inventory.items.size
        }
        onHighlightChanged(oldIndex, currentSlotIndex)
    }

    fun moveDown() {
        val oldIndex = currentSlotIndex
        currentSlotIndex += slotsPerRow
        currentSlotIndex %= Inventory.items.size
        onHighlightChanged(oldIndex, currentSlotIndex)
    }

    //this is a temporary function to check whether it correctly removes items from inventory
    fun dropItem() {
        val itemData = Inventory.itemAt(currentSlot().inventoryIndex)
        if (itemData.item == null) return
        itemData.amount -= 1
        itemData.item = if (itemData.amount <= 0) null else itemData.item
        currentSlot().updateDisplay()
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
