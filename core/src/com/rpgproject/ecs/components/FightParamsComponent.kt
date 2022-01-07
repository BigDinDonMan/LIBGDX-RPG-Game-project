package com.rpgproject.ecs.components

import com.artemis.Component

class FightParamsComponent : Component() {
    var maxHealth = 100
    var health = maxHealth
    var maxStamina = 150
    var stamina = maxStamina
}
