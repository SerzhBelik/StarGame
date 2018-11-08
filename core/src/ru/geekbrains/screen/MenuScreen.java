package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Exit_btn;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.Start_btn;
import ru.geekbrains.sprite.Title;

public class MenuScreen extends BaseScreen {
    private static final int STAR_COUNT = 256;
    private Background background;
    private Texture bgTexture;
    private TextureAtlas textureAtlas;
    private Star[] stars;
    private Start_btn start_btn;
    private Exit_btn exit_btn;
    private Vector2 touch;
    private StarGame starGame;
    private Title title;
    private Texture titleTxtr;

    protected Music mainTheme;

    public MenuScreen(StarGame starGame) {
        super();
        this.starGame = starGame;

    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("atlas.atlas");
        titleTxtr = new Texture("StarGame.png");
        title = new Title(new TextureRegion(titleTxtr));
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas, Star.getStarName());
        }
        start_btn = new Start_btn(textureAtlas);
        exit_btn = new Exit_btn(textureAtlas);

        mainTheme = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainTheme.mp3")); // Music: https://www.bensound.com
        mainTheme.setLooping(true);
        mainTheme.setVolume(0.2f);
        mainTheme.play();
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
            Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            background.draw(batch);
            for (int i = 0; i < stars.length; i++) {
                stars[i].draw(batch);
            }
            start_btn.draw(batch);
            exit_btn.draw(batch);
            title.draw(batch);
            batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.worldBounds = worldBounds;
        background.resize(worldBounds);
        title.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        mainTheme.dispose();
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
        start_btn.btnTouchDown(touch);
        exit_btn.btnTouchDown(touch);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (exit_btn.btnTouchUp(touch)) Gdx.app.exit();
        if (start_btn.btnTouchUp(touch))starGame.setScreen(new GameScreen());

        return false;
    }
}
