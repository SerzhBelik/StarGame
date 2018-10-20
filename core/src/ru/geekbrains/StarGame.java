package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backGround;
	Texture spaceShip;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		backGround = new Texture("background.jpg");
		spaceShip = new Texture("ship.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.123f, 0.53f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backGround, 0, 0);
		batch.draw(spaceShip, 10, 10);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
	}
}
