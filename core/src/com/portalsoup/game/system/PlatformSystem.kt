package com.portalsoup.game.system

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.portalsoup.game.entity.Entity
import com.portalsoup.game.entity.VectorRectangleEntity
import java.lang.RuntimeException

class PlatformSystem(val shapeRenderer: ShapeRenderer): System(Int.MIN_VALUE) {

    override fun addEntity(entity: Entity) {
        when (entity) {
            is VectorRectangleEntity -> {
                entity.shapeRenderer = shapeRenderer
                entities.add(entity)
            }
            else -> throw RuntimeException("Only vector based platforms for now!")
        }
    }
}