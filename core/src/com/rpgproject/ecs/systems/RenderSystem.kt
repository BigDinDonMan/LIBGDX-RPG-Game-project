package com.rpgproject.ecs.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.SkipWire
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rpgproject.ecs.components.ShaderComponent
import com.rpgproject.ecs.components.TextureComponent
import com.rpgproject.ecs.components.TransformComponent

@All(TransformComponent::class, TextureComponent::class)
class RenderSystem(private val spriteBatch: SpriteBatch, private val camera: Camera) : IteratingSystem() {

    @Wire
    var transformMapper: ComponentMapper<TransformComponent>? = null

    @Wire
    var textureMapper: ComponentMapper<TextureComponent>? = null

    @Wire
    var shaderMapper: ComponentMapper<ShaderComponent>? = null

    @SkipWire
    private var entityComparator: Comparator<Int> = Comparator { o1, o2 ->
        val t1 = transformMapper!!.get(o1)
        val t2 = transformMapper!!.get(o2)
        t1.position.z.compareTo(t2.position.z)
    }

    override fun begin() {
        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
    }

    override fun process(entityId: Int) {
        val transform = transformMapper?.get(entityId)
        val texture = textureMapper?.get(entityId)
        val shaderComp = shaderMapper?.get(entityId)
        if (texture?.texture == null) return
        spriteBatch.color = texture.color
        if (shaderComp != null) {
            spriteBatch.shader = shaderComp.shader
            applyShaderParams(shaderComp)
        } else {
            spriteBatch.shader = null
        }

        spriteBatch.draw(
            texture.region, transform!!.position.x, transform.position.y,
            transform.origin().x, transform.origin.y,
            transform.width(), transform.height(),
            1f, 1f, transform.rotationAngle
        )
    }

    override fun end() {
        spriteBatch.end()
    }

    private fun applyShaderParams(shaderComponent: ShaderComponent) {
        val shader = shaderComponent.shader ?: return
        val params = shaderComponent.shaderParams
        for (entry in params.entries) {
            val glType = entry.key
            for (varEntry in entry.value.entries) {
                glType.apply(shader, varEntry.key, varEntry.value)
            }
        }
    }
}