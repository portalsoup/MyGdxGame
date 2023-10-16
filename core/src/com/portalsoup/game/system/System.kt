package com.portalsoup.game.system

import com.portalsoup.game.entity.Entity

abstract class System(val priority: Int) {

    internal val entities: MutableList<Entity> = mutableListOf()

    internal inline fun <reified E: Entity> getEntity(): E? = entities.filterIsInstance<E>().firstOrNull()

    open fun addEntity(entity: Entity) { entities.add(entity) }

    open fun update() {
        entities.onEach {
            it.update()
            it.render()
        }
    }
}