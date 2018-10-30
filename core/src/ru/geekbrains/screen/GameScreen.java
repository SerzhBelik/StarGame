package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 256;
    private Background background;
    private Texture bgTexture;
    private TextureAtlas textureAtlas;
    private Star[] stars;
    private Vector2 touch;
    private StarGame starGame;

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("atlas.atlas");
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas, Star.getStarName());
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer){
        this.touch = touch;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
