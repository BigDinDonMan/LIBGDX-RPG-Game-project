package com.rpgproject.ecs.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event

abstract class EntityEvent(val entity: Entity) : Event