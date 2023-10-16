package com.portalsoup.game.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.MathUtils
import com.portalsoup.game.component.PhysicsComponent
import com.portalsoup.game.component.PlayerComponent
import com.portalsoup.game.component.RenderComponent
import com.portalsoup.game.entity.Entity
import com.portalsoup.game.entity.PlayerEntity

class PlayerSystem: System(Int.MAX_VALUE) {

    override fun addEntity(entity: Entity) {
        entities.add(entity)
    }

    fun updateCamera(camera: Camera) {
        getEntity<PlayerEntity>()?.also {
            val renderComponent = it.getComponent<RenderComponent>()!!
            val lerpAlpha = 0.1f

            camera.position.x = MathUtils.lerp(camera.position.x, renderComponent.x, lerpAlpha)
            camera.position.y = MathUtils.lerp(camera.position.y, renderComponent.y, lerpAlpha)

            camera.update()
        }
    }


    fun handleInput(player: PlayerEntity) {
        val intensity = 1f
        val playerComponent = player.getComponent<PlayerComponent>()!!
        val physicsComponent = player.getComponent<PhysicsComponent>()!!

        if (Gdx.input.isKeyPressed(playerComponent.forward) && (physicsComponent.velocity.y < physicsComponent.maxVelocity.y)) {
            physicsComponent.velocity.add(0f, intensity)
        }
        if (Gdx.input.isKeyPressed(playerComponent.backwards) && (physicsComponent.velocity.y < physicsComponent.maxVelocity.y)) {
            physicsComponent.velocity.add(0f, -intensity)
        }
        if (Gdx.input.isKeyPressed(playerComponent.left) && (physicsComponent.velocity.y < physicsComponent.maxVelocity.y)) {
            physicsComponent.velocity.add(-intensity, 0f)
        }
        if (Gdx.input.isKeyPressed(playerComponent.right) && (physicsComponent.velocity.y < physicsComponent.maxVelocity.y)) {
            physicsComponent.velocity.add(intensity, 0f)
        }
    }
}