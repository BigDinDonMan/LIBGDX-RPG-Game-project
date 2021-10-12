package com.rpgproject.ecs.systems

import com.artemis.BaseSystem
import com.rpgproject.ecs.events.specific.ExperienceGainEvent
import net.mostlyoriginal.api.event.common.Subscribe

class LevellingSystem : BaseSystem() {


    override fun processSystem() {}

    @Subscribe
    fun getExperienceData(e: ExperienceGainEvent) {

    }
}