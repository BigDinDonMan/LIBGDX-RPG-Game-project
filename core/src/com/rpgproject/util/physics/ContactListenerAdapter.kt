package com.rpgproject.util.physics

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

open class ContactListenerAdapter : ContactListener {
    override fun beginContact(contact: Contact?) {}

    override fun endContact(contact: Contact?) {}

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
}