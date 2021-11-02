package com.rpgproject.ecs.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.Entity
import com.artemis.annotations.Wire
import com.rpgproject.ecs.components.levelling.LevelDataComponent
import com.rpgproject.ecs.events.specific.ExperienceGainEvent
import net.mostlyoriginal.api.event.common.Subscribe

class LevellingSystem : BaseSystem() {

    var playerEntity: Entity? = null

    @Wire
    var levelDataMapper: ComponentMapper<LevelDataComponent>? = null

    override fun processSystem() {}

    @Subscribe
    fun getExperienceData(e: ExperienceGainEvent) {
    }
}