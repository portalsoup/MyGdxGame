package com.portalsoup.game.entity

import com.portalsoup.game.component.Component

abstract class Entity {

    abstract val components: List<Component>

    inline fun <reified C> getComponent(): C? = components.filterIsInstance<C>().firstOrNull()

    abstract fun update()

    abstract fun render()
}