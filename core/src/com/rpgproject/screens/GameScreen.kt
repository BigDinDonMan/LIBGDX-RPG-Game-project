package com.rpgproject.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.rpgproject.ecs.components.HealthComponent
import com.rpgproject.ecs.events.EventManager
import com.rpgproject.ecs.events.EventSystem
import com.rpgproject.ecs.events.HealthChangeEvent
import com.rpgproject.ecs.events.PlayerInputEvent
import com.rpgproject.ecs.systems.HealthChangeSystem
import com.rpgproject.util.ecs.removeAllSystems
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with

class GameScreen(private val engine: Engine) : KtxScreen {

    private val testEntity: Entity = engine.entity {
        with<HealthComponent>()
    }

    init {
        setUpEventManagerAndSystems()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun dispose() {
        engine.removeAllEntities()
        engine.removeAllSystems()
    }

    override fun render(delta: Float) {
        engine.update(delta)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            EventManager.queueEvent(HealthChangeEvent(testEntity, -10))
        }
    }

    private fun setUpEventManagerAndSystems() {
        val healthChangeSystem = HealthChangeSystem()
        EventManager.registerSystem(healthChangeSystem, HealthChangeSystem::class, HealthChangeEvent::class)
        engine.addSystem(healthChangeSystem)
    }
}