package com.rpgproject.util.timers

abstract class AbstractTimer(var looping: Boolean = false, val elapsedAction: () -> Unit = {}) {
    protected var elapsed = 0f
    protected var started = false

    fun start() {
        elapsed = 0f
        started = true
    }

    fun resume() {
        started = true
    }

    fun stop() {
        started = false
    }

    fun elapsedTime() = elapsed

    abstract fun update(delta: Float)
}