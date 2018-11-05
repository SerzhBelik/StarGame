package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ShipPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

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
    private ShipPool shipPool;
    private EnemyShip enemyShip;

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));

        textureAtlas = new TextureAtlas("atlas.atlas");
        mainAtlas = new TextureAtlas("mainAtlas.tpack");
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas, Star.getStarName());
        }
        bulletPool = new BulletPool();
        shipPool = new ShipPool();
        mainShip = new MainShip(mainAtlas, bulletPool);
        enemyShip = shipPool.obtain();
        enemyShip.set(
                new Vector2(Rnd.nextFloat(-0.375f + enemyShip.getHalfWidth(), 0.375f - enemyShip.getHalfWidth()), 0.5f + enemyShip.getHeight()),
                new Vector2(0, -0.2f)
        );

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
        shipPool.updateActiveObject(delta);
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
        shipPool.drawActiveObject(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        enemyShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public void deleteAllDestroyed(){
        bulletPool.freeAllDesrtoyedActiveObject();
        shipPool.freeAllDesrtoyedActiveObject();
    }
}
