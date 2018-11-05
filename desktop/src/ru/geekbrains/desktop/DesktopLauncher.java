package ru.geekbrains.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.geekbrains.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 3f/4f;
		config.height = 600;
		config.width = (int)(config.height * aspect);
		config.resizable = false;
		new LwjglApplication(new StarGame(), config);
		Music mainTheme = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainTheme.mp3")); // Music: https://www.bensound.com
		mainTheme.setLooping(true);
		mainTheme.setVolume(0.3f);
		mainTheme.play();
	}
}
