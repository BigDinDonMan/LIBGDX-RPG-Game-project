package com.rpgproject.ecs.events.specific

import com.artemis.Entity
import com.rpgproject.ecs.events.EntityEvent

class AttackEvent(attackingEntity: Entity, val attackDirectionX: Float, val attackDirectionY: Float) : EntityEvent(attackingEntity)