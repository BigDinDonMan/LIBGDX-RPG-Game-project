package com.rpgproject.ecs.systems

import com.artemis.systems.IteratingSystem
import com.rpgproject.ecs.events.specific.AnimationChangeEvent
import net.mostlyoriginal.api.event.common.Subscribe

class AnimationSystem : IteratingSystem() {

    @Subscribe
    fun changeAnimation(e: AnimationChangeEvent) {
        //if animation key is different:
        //step 1: clear animation elapsed time
        //step 2: set new animation key
    }

    override fun process(entityId: Int) {

    }
}