package com.rpgproject.ecs.events.specific

import net.mostlyoriginal.api.event.common.Event

data class CameraShakeEvent(val duration: Float /*seconds, e.g. 1.5 for one and a half*/, val magnitude: Float) : Event