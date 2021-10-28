package com.rpgproject.ecs.events.specific

import com.artemis.Entity
import com.rpgproject.ecs.events.EntityEvent

class AnimationChangeEvent(animatedEntity: Entity, val animationKey: String) : EntityEvent(animatedEntity)