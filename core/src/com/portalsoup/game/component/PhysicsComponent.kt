package com.portalsoup.game.component

import com.badlogic.gdx.math.Vector2

data class PhysicsComponent(
    var friction: Float = 0f, // Affects decrease in velocity of other entity when adjacent
    var velocity: Vector2 = Vector2(0f, 0f), // Current entity movement
    var maxVelocity: Vector2 = Vector2(50f, 50f),
    var minVelocity: Vector2 = Vector2(1f, 1f),
    var mass: Float = 1f // Affects change in velocity
): Component