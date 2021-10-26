package com.rpgproject.ecs.events.specific

import com.artemis.Entity
import com.rpgproject.ecs.events.EntityEvent

//target entity should always be player
class PlayerInputEvent(
        targetEntity: Entity,
        val lookDirectionX: Float,
        val lookDirectionY: Float,
        val dodge: Boolean,
        val attack: Boolean
) : EntityEvent(targetEntity)