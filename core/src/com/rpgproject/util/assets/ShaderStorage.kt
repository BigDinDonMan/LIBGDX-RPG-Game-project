package com.rpgproject.util.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.ObjectMap

object ShaderStorage {

    private val shadersMap = ObjectMap<String, ShaderProgram>()

    init {
        loadShaders()
    }

    private fun loadShaders() {
        //@NOTE: this shader wont work if there are pixels with transparency inside the texture
        shadersMap.put(
                "Outline",
                ShaderProgram(
                        Gdx.files.internal("shaders/outline-vertex.glsl"),
                        Gdx.files.internal("shaders/outline-fragment.glsl")
                )
        )
    }

    operator fun get(shaderName: String): ShaderProgram = shadersMap.get(shaderName)

    fun dispose() {
        shadersMap.values().forEach { it.dispose() }
    }
}
