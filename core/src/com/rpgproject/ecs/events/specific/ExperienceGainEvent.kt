package com.rpgproject.ecs.events.specific

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event

data class ExperienceGainEvent(val experience: Int, val monster: Entity) : Event