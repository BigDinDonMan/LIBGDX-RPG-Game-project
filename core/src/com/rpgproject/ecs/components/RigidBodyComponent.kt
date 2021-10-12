package com.rpgproject.ecs.components

import com.artemis.Component
import com.badlogic.gdx.physics.box2d.Body

class RigidBodyComponent : Component() {
    var physicsBody: Body? = null
}