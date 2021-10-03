package com.rpgproject.ecs.events

import com.badlogic.ashley.core.Entity

data class HealthChangeEvent(val targetEntity: Entity, val healthChange: Int) : Event()