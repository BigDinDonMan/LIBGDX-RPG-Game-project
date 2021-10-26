package com.rpgproject.ecs.components

import com.artemis.Component

class AttackComponent : Component() {
    var attackDamage = 0f
    var currentCooldown = 0f
    //todo: add array (or queue? and add popped element at the back) of attack cooldowns (so that combos are available for implementation)
}