package com.rpgproject.util.physics

import com.badlogic.gdx.physics.box2d.Contact
import net.mostlyoriginal.api.event.common.EventSystem

class CollisionListener(private val eventSystem: EventSystem) : ContactListenerAdapter() {


    //these methods should queue events
    override fun beginContact(contact: Contact?) {

    }

    override fun endContact(contact: Contact?) {
    }
}