package com.rpgproject.util.observer

class GameEvent<T> : Subscribable {
    private val subscriptions = mutableListOf<(T) -> Unit>()

    operator fun plusAssign(func: (T) -> Unit) {
        subscriptions += func
    }

    operator fun minusAssign(func: (T) -> Unit) {
        subscriptions -= func
    }

    operator fun invoke(arg: T) {
        subscriptions.forEach { it.invoke(arg) }
    }

    override fun clearListeners() {
        subscriptions.clear()
    }
}
