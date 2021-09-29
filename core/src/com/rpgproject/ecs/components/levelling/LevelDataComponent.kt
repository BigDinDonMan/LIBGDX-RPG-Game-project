package com.rpgproject.ecs.components.levelling

import com.badlogic.ashley.core.Component

class LevelDataComponent : Component {
    var currentLevel = 1
    var experienceToLevel = 0
    var skillPoints = 0
    var attributePoints = 0
}