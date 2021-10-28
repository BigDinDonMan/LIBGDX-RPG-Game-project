package com.rpgproject.ecs.components

import com.artemis.Component

class InteractableComponent : Component() {

    enum class InteractableObjectType {
        PICKUP;
    }

    lateinit var interactableType: InteractableObjectType
}