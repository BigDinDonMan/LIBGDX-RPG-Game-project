package com.rpgproject.ecs.components

import com.artemis.Component
import java.util.*

class AttackComponent : Component() {
    var attackDamage = 0f
    var attackRange = 0f
    var currentCooldown = 0f
    var cooldowns = LinkedList<Float>() //this will store cooldowns of each consecutive attack (remove at start, add to the back)
    var knockbackForce = 0f
    var damagableEntityTypes = ArrayList<String>()
}