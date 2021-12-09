package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TextureComponent : Component() {
    var texture: Texture? = null
        set(value) {
            field = value
            if (field != null) {
                region.setRegion(field)
            }
        }
    var region = TextureRegion()
    var color: Color = Color.WHITE
}