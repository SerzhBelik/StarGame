package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekbrains.screen.MenuScreen;
import ru.geekbrains.screen.NewScreen;

public class StarGame extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen());
	}

}
