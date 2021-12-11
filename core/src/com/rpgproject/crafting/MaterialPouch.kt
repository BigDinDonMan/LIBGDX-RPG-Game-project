package com.rpgproject.crafting

import com.badlogic.gdx.utils.ObjectMap

object MaterialPouch {
    private val materials = HashMap<CraftingMaterial, Int>()
    private val maxMaterialCounts = ObjectMap<MaterialRarity, Int>()

    init {
        maxMaterialCounts.put(MaterialRarity.COMMON, 0)
        maxMaterialCounts.put(MaterialRarity.UNCOMMON, 0)
        maxMaterialCounts.put(MaterialRarity.RARE, 0)
        maxMaterialCounts.put(MaterialRarity.UNIQUE, 0)
    }

    //returns the remaining (not added) material amount
    fun add(material: CraftingMaterial, amount: Int): Int {
        val currentAmount = materials.getOrDefault(material, 0)
        var newAmount = currentAmount + amount
        val amountBoundary = maxMaterialCounts[material.rarity]
        var returnValue = 0
        if (newAmount > amountBoundary) {
            newAmount = amountBoundary
            returnValue = newAmount - amountBoundary
        }
        materials[material] = newAmount
        return returnValue
    }
}
