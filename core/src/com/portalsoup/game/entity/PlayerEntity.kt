package com.portalsoup.game.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.portalsoup.game.component.*

class PlayerEntity(private val batch: SpriteBatch): Entity() {

    override val components: List<Component> = listOf(
        PlayerComponent().apply {
            sprite.setPosition(300f, 300f)
        },
        RenderComponent(
            x = 300f,
            y = 300f
        ),
        CollisionComponent(),
        PhysicsComponent()
    )

    fun getX() = getComponent<RenderComponent>()?.x
    fun getY() = getComponent<RenderComponent>()?.y

    override fun update() {
        components.onEach {
            when (it) {
                is PlayerComponent -> {
                    println("Current velocity: ${getComponent<PhysicsComponent>()?.velocity}")
                    updateVelocity()
                    updateMovement()
                }
            }
        }
    }

    override fun render() {
        components.onEach {
            when (it) {
                is PlayerComponent -> {
                    batch.begin()
                    it.sprite.draw(batch)
                    batch.end()
                }
            }
        }
    }

    private fun updateMovement() {
        val playerComponent = getComponent<PlayerComponent>()!!
        val renderComp = getComponent<RenderComponent>()!!
        playerComponent.sprite.setPosition(renderComp.x, renderComp.y)
    }

    private fun updateVelocity() {
        val delta = Gdx.graphics.deltaTime
        val renderComp = getComponent<RenderComponent>()!!
        val physicsComp = getComponent<PhysicsComponent>()!!

        renderComp.x += physicsComp.velocity.x * delta
        renderComp.y += physicsComp.velocity.y * delta

        decay()
    }

    private fun decay() {
        val physicsComponent = getComponent<PhysicsComponent>()!!

        val decayFactor = .98f

        val oldX = physicsComponent.velocity.x
        val oldY = physicsComponent.velocity.y

        val newX = oldX * decayFactor
        val newY = oldY * decayFactor


        physicsComponent.velocity.set(
            newX,
            newY
        )
    }

    private fun decayVelocity() {
        val delta = Gdx.graphics.deltaTime
        val minVelocity = 1f
        val deceleration = 0.5f

        val playerComponent = getComponent<PlayerComponent>()!!
        val physicsComponent = getComponent<PhysicsComponent>()!!

        if (!Gdx.input.isKeyPressed(playerComponent.forward)) {
            if (physicsComponent.velocity.y > minVelocity) {
                physicsComponent.velocity.sub(deceleration * delta, 0f)
            } else {
                physicsComponent.velocity.set(0f, physicsComponent.velocity.y)
            }
        }

        if (!Gdx.input.isKeyPressed(playerComponent.backwards)) {
            if (physicsComponent.velocity.y < -minVelocity) {
                physicsComponent.velocity.sub(deceleration * delta, 0f)
            } else {
                physicsComponent.velocity.set(0f, physicsComponent.velocity.y)
            }
        }

        if (!Gdx.input.isKeyPressed(playerComponent.left)) {
            if (physicsComponent.velocity.x > minVelocity) {
                physicsComponent.velocity.sub(0f, deceleration * delta)
            } else {
                physicsComponent.velocity.set(physicsComponent.velocity.x, 0f)
            }
        }

        if (!Gdx.input.isKeyPressed(playerComponent.left)) {
            if (physicsComponent.velocity.x < -minVelocity) {
                physicsComponent.velocity.sub(0f, deceleration * delta)
            } else {
                physicsComponent.velocity.set(physicsComponent.velocity.x, 0f)
            }
        }
    }
}