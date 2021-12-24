package com.rpgproject.inventory

import com.badlogic.gdx.graphics.Texture

//this is solely a data class for displaying the item in the UI; use entities for implementing pickup logic
//maybe add subclasses to this class later that do different things, e.g. potions, food, relics
//todo: think about implementing translations (will probably need to store item key instead of name, e.g. grand-relic -> Grand Relic of Something)
//todo: add equals and hash code overrides or make it into a data class
class InventoryItem(val name: String, val description: String, val icon: Texture, val maxStack: Int)
