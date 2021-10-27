package com.rpgproject.ecs.components

import com.artemis.Component
import java.util.*

class AttackComponent : Component() {
    var attackDamage = 0f
    var attackRange = 0f
    var currentCooldown = 0f
    var cooldowns = LinkedList<Int>() //this will store cooldowns of each consecutive attack (remove at start, add to the back)

    //todo: add a list of entity types that can be damaged by the attacks
    var damagableEntityTypes = ArrayList<String>()
}