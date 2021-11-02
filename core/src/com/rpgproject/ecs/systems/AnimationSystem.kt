package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.rpgproject.ecs.components.AnimationComponent
import com.rpgproject.ecs.events.specific.AnimationChangeEvent
import net.mostlyoriginal.api.event.common.Subscribe

class AnimationSystem : IteratingSystem() {

    @Wire
    var animationMapper: ComponentMapper<AnimationComponent>? = null

    @Subscribe
    fun changeAnimation(e: AnimationChangeEvent) {
        //if animation key is different:
        //step 1: clear animation elapsed time
        //step 2: set new animation key
        val animationComponent = animationMapper!!.get(e.entity)
        if (animationComponent.currentKey != e.animationKey) {
            animationComponent.elapsedTime = 0f
            animationComponent.currentKey = e.animationKey
        }
    }

    override fun process(entityId: Int) {

    }
}