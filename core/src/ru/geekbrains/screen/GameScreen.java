package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemiesEmmiter;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;
    private Background background;
    private Texture bgTexture;
    private TextureAtlas textureAtlas;
    private TextureAtlas mainAtlas;
    private Star[] stars;
    private Vector2 touch;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Sound laserSound;
    private Sound bulletSound;
    EnemiesEmmiter enemiesEmmiter;

    @Override
    public void show() {
        super.show();
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.mp3"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));

        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));

        textureAtlas = new TextureAtlas("atlas.atlas");
        mainAtlas = new TextureAtlas("mainAtlas.tpack");
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas, Star.getStarName());
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound);
        mainShip = new MainShip(mainAtlas, bulletPool, laserSound);
        enemiesEmmiter = new EnemiesEmmiter(enemyPool, worldBounds, mainAtlas);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveObject(delta);
        enemyPool.updateActiveObject(delta);
        enemiesEmmiter.generate(delta);
    }

    public void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveObject(batch);
        enemyPool.drawActiveObject(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        mainAtlas.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer){
        this.touch = touch;
        mainShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return false;
    }



    public void deleteAllDestroyed(){
        bulletPool.freeAllDesrtoyedActiveObject();
        enemyPool.freeAllDesrtoyedActiveObject();
    }
}
