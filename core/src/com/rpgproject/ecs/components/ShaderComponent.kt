package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.rpgproject.util.ShaderParams
import com.rpgproject.util.collections.DefaultMap

//note: shader attributes and variables should be set between begin() and end() calls in sprite batch
class ShaderComponent : Component() {
    var shader: ShaderProgram? = null
    var shaderParams: ShaderParams =
            DefaultMap { DefaultMap<String, Array<Float>> { arrayOf(0f, 0f, 0f, 0f) } }
}