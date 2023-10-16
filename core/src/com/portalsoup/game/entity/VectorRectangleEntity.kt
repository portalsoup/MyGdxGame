package com.portalsoup.game.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.portalsoup.game.component.*

class VectorRectangleEntity(x: Float, y: Float, width: Float, height: Float): Entity() {

    override val components: List<Component> = listOf(
        VectorRectangleComponent(),
        RenderComponent(
            x = x,
            y = y,
            width = width,
            height = height
        ),
        CollisionComponent(Rectangle(x, y, width, height))
    )

    lateinit var shapeRenderer: ShapeRenderer


    override fun update() {}

    override fun render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        components.onEach {
            when (it) {
                is VectorRectangleComponent -> {
                    shapeRenderer.color = it.color
                }
                is RenderComponent -> {
                    shapeRenderer.rect(it.x, it.y, it.width, it.height)
                }
            }
        }
        shapeRenderer.end()
    }
}