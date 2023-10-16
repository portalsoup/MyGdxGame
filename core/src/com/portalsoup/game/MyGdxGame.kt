package com.portalsoup.game;

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.portalsoup.game.entity.PlayerEntity
import com.portalsoup.game.entity.VectorRectangleEntity
import com.portalsoup.game.system.CollisionSystem

import com.portalsoup.game.system.PlatformSystem
import com.portalsoup.game.system.PlayerSystem
import com.portalsoup.game.system.System

class MyGdxGame : ApplicationAdapter() {

	lateinit var player: PlayerEntity

	lateinit var systems: MutableList<System>
	lateinit var camera: OrthographicCamera

	lateinit var shapeRenderer: ShapeRenderer
	lateinit var batch: SpriteBatch

	override fun create() {
		systems = mutableListOf()

		camera = OrthographicCamera()
		camera.position.set(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f, 0f)
		camera.update()
		camera.setToOrtho(false, 800f, 800f)

		shapeRenderer = ShapeRenderer()

		batch = SpriteBatch()
		batch.projectionMatrix = camera.combined

		val floor = VectorRectangleEntity(
			0f,
			0f,
			Gdx.graphics.width.toFloat(),
			50f
		)
		val platforms = PlatformSystem(shapeRenderer).apply {
			addEntity(floor)
		}

		player = PlayerEntity(batch)
		val playerSystem = PlayerSystem().apply {
			addEntity(player)
		}

		val collisionSystem = CollisionSystem().apply {
			addEntity(floor)
			addEntity(player)
		}

		systems.add(platforms)
		systems.add(playerSystem)
		systems.add(collisionSystem)
	}

	override fun render() {
		systems.filterIsInstance<PlayerSystem>().first().apply {
			handleInput(player)
			updateCamera(camera)
		}

		Gdx.gl.glClearColor(0f, 5f, 5f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		systems.onEachByPriority { it.update() }
	}


	override fun dispose() {
		batch.dispose()
	}
}

fun List<System>.onEachByPriority(f: (System) -> Unit) {
	sortedBy { it.priority }.reversed().onEach(f)
}