package com.rpgproject.util.ecs

import com.badlogic.ashley.core.Entity
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.util.Mappers
import java.util.Comparator

class EntityComparator : Comparator<Entity> {
    override fun compare(o1: Entity, o2: Entity): Int {
        val t1 = Mappers.getComponent<TransformComponent>(o1)
        val t2 = Mappers.getComponent<TransformComponent>(o2)
        return t1.position.z.compareTo(t2.position.z)
    }
}