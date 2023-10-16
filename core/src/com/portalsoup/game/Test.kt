package com.portalsoup.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SimpleSpriteGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var sprite: Sprite

    override fun create() {
        batch = SpriteBatch()

        // Load a texture (replace 'image.png' with your image file)
        val texture = Texture("player.png")

        // Create a sprite using the texture
        sprite = Sprite(texture)

        // Set the initial position of the sprite
        sprite.setPosition(100f, 100f)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        sprite.draw(batch) // Draw the sprite
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        sprite.texture.dispose()
    }
}