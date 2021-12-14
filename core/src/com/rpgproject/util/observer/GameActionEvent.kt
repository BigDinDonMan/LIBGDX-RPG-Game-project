package com.rpgproject.util.observer

class GameActionEvent : Subscribable {
    private val subscriptions = mutableListOf<() -> Unit>()

    operator fun plusAssign(func: () -> Unit) {
        subscriptions += func
    }

    operator fun minusAssign(func: () -> Unit) {
        subscriptions -= func
    }

    operator fun invoke() {
        subscriptions.forEach { it.invoke() }
    }

    override fun clearListeners() {
        subscriptions.clear()
    }
}
