package com.rpgproject.desktop

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.rpgproject.RPGProjectGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            foregroundFPS = 60
            width = 1366
            height = 768
//            fullscreen = true
        }
        LwjglApplication(RPGProjectGame(), config)
    }
}