package com.rpgproject.util.physics

import com.artemis.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.RayCastCallback

class AttackRayCastCallback(vararg filters: String) : RayCastCallback {

    private val hits = ArrayList<Entity>() //todo: make a method returning this list (or a copy of it) to access hit entities
    private val tagFilters = filters.toList()

    override fun reportRayFixture(fixture: Fixture?, point: Vector2?, normal: Vector2?, fraction: Float): Float {
        return 0f
    }
}