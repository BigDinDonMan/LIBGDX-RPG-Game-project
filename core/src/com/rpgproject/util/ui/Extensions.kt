package com.rpgproject.util.ui

import com.badlogic.gdx.scenes.scene2d.Stage

fun Stage.update(delta: Float) = this.act(delta).also { this.draw() }