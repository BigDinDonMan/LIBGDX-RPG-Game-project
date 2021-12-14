package com.rpgproject.util.observer

class TripleArgGameEvent<T1, T2, T3> : Subscribable {
    val subscriptions = mutableListOf<(T1, T2, T3) -> Unit>()

    operator fun plusAssign(func: (T1, T2, T3) -> Unit) {
        subscriptions += func
    }

    operator fun minusAssign(func: (T1, T2, T3) -> Unit) {
        subscriptions -= func
    }

    operator fun invoke(arg0: T1, arg1: T2, arg2: T3) {
        subscriptions.forEach { it.invoke(arg0, arg1, arg2) }
    }

    override fun clearListeners() {
        subscriptions.clear()
    }
}
