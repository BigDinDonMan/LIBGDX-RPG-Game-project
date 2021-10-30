package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.graphics.glutils.ShaderProgram

//note: shader attributes and variables should be set between begin() and end() calls in sprite batch
class ShaderComponent : Component() {
    var shader: ShaderProgram? = null
    var shaderParams: Any? = null //todo: think about how to pass this
}