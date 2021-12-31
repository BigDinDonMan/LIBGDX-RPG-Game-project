package com.rpgproject.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.rpgproject.MythosHunterGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            foregroundFPS = 60
            width = 1366
            height = 768
//            fullscreen = true
        }
        LwjglApplication(MythosHunterGame(), config)
    }
}
