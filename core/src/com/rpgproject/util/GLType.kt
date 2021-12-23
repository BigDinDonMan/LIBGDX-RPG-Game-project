package com.rpgproject.util

import com.badlogic.gdx.graphics.glutils.ShaderProgram

enum class GLType {
    FLOAT {
        override fun apply(shader: ShaderProgram, varName: String, params: Array<Float>) {
            shader.setUniformf(varName, params[0])
        }

        override fun apply(shader: ShaderProgram, varName: String, params: FloatArray) {
            shader.setUniformf(varName, params[0])
        }
    },
    VEC2 {
        override fun apply(shader: ShaderProgram, varName: String, params: Array<Float>) {
            shader.setUniformf(varName, params[0], params[1])
        }

        override fun apply(shader: ShaderProgram, varName: String, params: FloatArray) {
            shader.setUniformf(varName, params[0], params[1])
        }
    },
    VEC3 {
        override fun apply(shader: ShaderProgram, varName: String, params: Array<Float>) {
            shader.setUniformf(varName, params[0], params[1], params[2])
        }

        override fun apply(shader: ShaderProgram, varName: String, params: FloatArray) {
            shader.setUniformf(varName, params[0], params[1], params[2])
        }
    },
    VEC4 {
        override fun apply(shader: ShaderProgram, varName: String, params: Array<Float>) {
            shader.setUniformf(varName, params[0], params[1], params[2], params[3])
        }

        override fun apply(shader: ShaderProgram, varName: String, params: FloatArray) {
            shader.setUniformf(varName, params[0], params[1], params[2], params[3])
        }
    };

    abstract fun apply(shader: ShaderProgram, varName: String, params: Array<Float>)
    abstract fun apply(shader: ShaderProgram, varName: String, params: FloatArray)
}
