package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

class TextureComponent : Component() {
    var texture: Texture? = null

    //maybe add optional shader & color?
    var color: Color = Color.WHITE
}