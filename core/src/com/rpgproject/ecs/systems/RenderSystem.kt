package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.SkipWire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rpgproject.ecs.components.TextureComponent
import com.rpgproject.ecs.components.TransformComponent

@All(TransformComponent::class, TextureComponent::class)
class RenderSystem(private val spriteBatch: SpriteBatch, private val camera: Camera) : IteratingSystem() {

    private lateinit var transformMapper: ComponentMapper<TransformComponent>
    private lateinit var textureMapper: ComponentMapper<TextureComponent>

    @SkipWire
    private var entityComparator: Comparator<Int> = Comparator { o1, o2 ->
        val t1 = transformMapper.get(o1)
        val t2 = transformMapper.get(o2)
        t1.position.z.compareTo(t2.position.z)
    }

    override fun begin() {
        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
    }

    override fun process(entityId: Int) {
        val transform = transformMapper.get(entityId)
        val texture = textureMapper.get(entityId)
        if (texture.texture == null) return
        spriteBatch.draw(texture.texture, transform.position.x, transform.position.y)
    }

    override fun end() {
        spriteBatch.end()
    }
}