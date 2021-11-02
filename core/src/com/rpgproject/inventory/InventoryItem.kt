package com.rpgproject.inventory

import com.badlogic.gdx.graphics.Texture

//this is solely a data class for displaying the item in the UI; use entities for implementing pickup logic
//maybe add subclasses to this class later that do different things, e.g. potions, food, relics
class InventoryItem(val name: String, val description: String, val icon: Texture)