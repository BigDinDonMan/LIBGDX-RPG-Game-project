package com.rpgproject.util.observer

class DoubleArgGameEvent<T1, T2> : Subscribable {
    val subscriptions = mutableListOf<(T1, T2) -> Unit>()

    operator fun plusAssign(func: (T1, T2) -> Unit) {
        subscriptions += func
    }

    operator fun minusAssign(func: (T1, T2) -> Unit) {
        subscriptions -= func
    }

    operator fun invoke(arg0: T1, arg1: T2) {
        subscriptions.forEach { it.invoke(arg0, arg1) }
    }

    override fun clearListeners() {
        subscriptions.clear()
    }
}
