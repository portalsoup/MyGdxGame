package com.portalsoup.game.system

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.portalsoup.game.component.CollisionComponent
import com.portalsoup.game.component.PhysicsComponent
import com.portalsoup.game.component.PlayerComponent
import com.portalsoup.game.component.RenderComponent
import com.portalsoup.game.entity.Entity
import kotlin.math.min

class CollisionSystem: System(0) {

    override fun update() {
        super.update()

        entities.onEach { a ->
            entities.filter { it != a }.onEach { b ->
                if (checkCollision(a, b)) {
                    println("COLLISION!")
                    handleCollisionConservation(a, b)
                }
            }
        }
    }

    private fun checkCollision(a: Entity, b: Entity): Boolean {
        val boundsA = a.getComponent<CollisionComponent>()?.bounds ?: return false
        val boundsB = b.getComponent<CollisionComponent>()?.bounds ?: return false

        return boundsA.overlaps(boundsB)
    }

    private fun handleCollision(a: Entity, b: Entity) {
        val rectA = a.getComponent<CollisionComponent>()?.bounds!!
        val rectB = b.getComponent<CollisionComponent>()?.bounds!!

        // Calculate penetration depth in the x and y axes
        val penetrationX = if (rectA.x < rectB.x) {
            rectA.x + rectA.width - rectB.x
        } else {
            rectB.x + rectB.width - rectA.x
        }

        val penetrationY = if (rectA.y < rectB.y) {
            rectA.y + rectA.height - rectB.y
        } else {
            rectB.y + rectB.height - rectA.y
        }

        // Determine the minimum penetration direction (x or y)
        val minPenetration = if (penetrationX < penetrationY) penetrationX else penetrationY

        // Move the entity out of the collision in the direction of the minimum penetration
        if (penetrationX < penetrationY) {
            // Horizontal collision
            a.getComponent<RenderComponent>()!!.x += if (rectA.x < rectB.x) minPenetration else -minPenetration
        } else {
            // Vertical collision
            a.getComponent<RenderComponent>()!!.y += if (rectA.y < rectB.y) minPenetration else -minPenetration
        }
    }

    private fun handleCollisionConservation(entityA: Entity, entityB: Entity) {
        val collisionA = entityA.getComponent<CollisionComponent>()
        val physicsA = entityA.getComponent<PhysicsComponent>()
        val renderComponentA = entityA.getComponent<RenderComponent>()

        val collisionB = entityB.getComponent<CollisionComponent>()
        val physicsB = entityB.getComponent<PhysicsComponent>()
        val renderComponentB = entityB.getComponent<RenderComponent>()

        if (collisionA != null && collisionB != null) {
            val rectA = collisionA.bounds
            val rectB = collisionB.bounds

            if (rectA.overlaps(rectB)) {
                // Calculate penetration depth and collision normal
                val penetrationX = calculatePenetrationX(rectA, rectB)
                val penetrationY = calculatePenetrationY(rectA, rectB)
                val minPenetration = min(penetrationX, penetrationY)
                val collisionNormal = calculateCollisionNormal(rectA, rectB)

                // Calculate the mass ratio
                val massA = physicsA?.mass ?: 1.0f
                val massB = physicsB?.mass ?: 1.0f
                val massRatio = massB / (massA + massB)

                // Move entities away from each other scaled by their mass
                val separationX = collisionNormal.x * minPenetration * massRatio
                val separationY = collisionNormal.y * minPenetration * massRatio

                renderComponentA!!.x -= separationX
                renderComponentA!!.y -= separationY
                renderComponentB!!.x += separationX
                renderComponentB!!.y += separationY
            }
        }
    }

    private fun calculatePenetrationX(rectA: Rectangle, rectB: Rectangle): Float {
        return if (rectA.x < rectB.x) {
            rectA.x + rectA.width - rectB.x
        } else {
            rectB.x + rectB.width - rectA.x
        }
    }

    private fun calculatePenetrationY(rectA: Rectangle, rectB: Rectangle): Float {
        return if (rectA.y < rectB.y) {
            rectA.y + rectA .height - rectB.y
        } else {
            rectB.y + rectB.height - rectA.y
        }
    }

    private fun calculateCollisionNormal(rectA: Rectangle, rectB: Rectangle): Vector2 {
        // Calculate the center of both rectangles
        val centerA = Vector2(rectA.x + rectA.width / 2, rectA.y + rectA.height / 2)
        val centerB = Vector2(rectB.x + rectB.width / 2, rectB.y + rectB.height / 2)

        // Calculate the vector from A to B and normalize
        return Vector2(centerB.x - centerA.x, centerB.y - centerA.y).nor()
    }
}