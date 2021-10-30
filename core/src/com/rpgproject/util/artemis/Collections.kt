package com.rpgproject.util.artemis

import com.artemis.utils.IntBag

fun IntBag.forEach(func: (Int) -> Unit) {
    for (i in 0..this.size())
        func.invoke(this.get(i))
}