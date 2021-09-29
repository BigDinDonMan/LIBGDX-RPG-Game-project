package com.rpgproject.util.observers

interface Publisher<T> {
    fun notifyObservers()
    fun addObserver(o: Observer<T>)
    fun removeObserver(o: Observer<T>)
}