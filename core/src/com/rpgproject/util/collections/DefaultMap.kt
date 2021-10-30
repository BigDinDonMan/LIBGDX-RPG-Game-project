package com.rpgproject.util.collections

class DefaultMap<K, V>(private val defaultValueFactory: () -> V) : HashMap<K, V>() {
    override fun get(key: K): V? {
        if (!containsKey(key)) {
            val default = defaultValueFactory.invoke()
            this[key] = default
            return default
        }
        return super.get(key)
    }
}