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

    val ATTR_POINTS_PER_LEVEL = 4

    @Wire
    var levelDataMapper: ComponentMapper<LevelDataComponent>? = null

    override fun processSystem() {}

    @Subscribe
    fun getExperienceData(e: ExperienceGainEvent) {
        val levelDataComponent = levelDataMapper!!.get(playerEntity)
        addExperience(levelDataComponent, e)
        addKillStatistic(e)
    }

    private fun addExperience(levelData: LevelDataComponent, e: ExperienceGainEvent) {
        levelData.currentExperience += e.experience
        if (levelData.currentExperience >= levelData.experienceToLevel) {
            levelUp(levelData)
        }
        //todo: add onExperienceGained event to display changes in the UI
    }

    private fun levelUp(levelData: LevelDataComponent) {
        levelData.currentExperience = (levelData.currentExperience % levelData.experienceToLevel)
        levelData.currentLevel++
        levelData.skillPoints++
        levelData.attributePoints += ATTR_POINTS_PER_LEVEL
    }

    private fun addKillStatistic(e: ExperienceGainEvent) {
    }

    fun injectPlayerEntity(e: Entity) {
        this.playerEntity = e
    }
}