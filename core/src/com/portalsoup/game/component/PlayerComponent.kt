package com.portalsoup.game.component

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class PlayerComponent(
    var sprite: Sprite = Sprite(Texture("mario.png")),
    var forward: Int = Input.Keys.W,
    var backwards: Int = Input.Keys.S,
    var left: Int = Input.Keys.A,
    var right: Int = Input.Keys.D,
    var alive: Boolean = true
): Component