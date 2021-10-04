package com.rpgproject.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rpgproject.ecs.components.TextureComponent
import com.rpgproject.ecs.components.TransformComponent
import com.rpgproject.util.ecs.EntityComparator

//this should be a normal processing system
class RenderSystem(val spriteBatch: SpriteBatch, val renderCamera: Camera) : SortedIteratingSystem(Family.all(TextureComponent::class.java, TransformComponent::class.java).get(), EntityComparator()) {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
    }
}