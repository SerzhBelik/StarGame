package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.HealthPoint;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.NewGame;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemiesEmmiter;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;
    private int frags;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    private Background background;
    private Texture bgTexture;
    private GameOver gameOver;
    private NewGame newGame;
    private HealthPoint healthPoint;
    private TextureAtlas textureAtlas;
    private TextureAtlas mainAtlas;
    private Star[] stars;
    private Vector2 touch;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private Sound laserSound;
    private Sound bulletSound;
    Music mainTheme;
    private Sound explosionSound;
    private EnemiesEmmiter enemiesEmmiter;
    private StarGame starGame;
    private float gameOverInterval = 1.5f;
    private enum State {PLAYING, GAME_OVER}
    private State state;
    private Font font;


    public GameScreen(StarGame starGame, Music mainTheme) {
        super();
        this.starGame = starGame;
        this.mainTheme = mainTheme;

    }

    @Override
    public void show() {
        super.show();
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.mp3"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));

        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));

        textureAtlas = new TextureAtlas("atlas.atlas");
        mainAtlas = new TextureAtlas("mainAtlas.tpack");

        gameOver = new GameOver(mainAtlas);
        newGame = new NewGame(mainAtlas);
        healthPoint = new HealthPoint(new TextureRegion(new Texture("hp.png")));

        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas, Star.getStarName());
        }
        explosionPool = new ExplosionPool(mainAtlas, explosionSound);
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, bulletSound);
        mainShip = new MainShip(mainAtlas, bulletPool, explosionPool, laserSound);
        enemiesEmmiter = new EnemiesEmmiter(enemyPool, worldBounds, mainAtlas);

        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(0.03f);

        startNewGame();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        explosionPool.updateActiveObject(delta);
//        if (mainShip.isDestroyed()) {
//            gameOverInterval -= delta;
//            return;
//        }
//        mainShip.update(delta);
//        bulletPool.updateActiveObject(delta);
//        enemyPool.updateActiveObject(delta);
//        enemiesEmmiter.generate(delta);

        switch (state){
            case PLAYING:
                mainShip.update(delta);
                healthPoint.update(delta, mainShip.getHp());
                bulletPool.updateActiveObject(delta);
                enemyPool.updateActiveObject(delta);
                enemiesEmmiter.generate(delta, frags);
                if (mainShip.isDestroyed()){
                    state = State.GAME_OVER;
                }
                break;
            case GAME_OVER:
                gameOverInterval -= delta;

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
//        if (!mainShip.isDestroyed()) {
//            mainShip.draw(batch);
//
//            bulletPool.drawActiveObject(batch);
//            enemyPool.drawActiveObject(batch);
//        } else {
//            System.out.print("game over");
//            if (gameOverInterval < 0) {
//                gameOver.draw(batch);
//                newGame.draw(batch);
//            }
//        }
        explosionPool.drawActiveObject(batch);
        if (state == State.GAME_OVER){
            if (gameOverInterval < 0){
                gameOver.draw(batch);
                newGame.draw(batch);
            }

        } else {
            mainShip.draw(batch);
            explosionPool.drawActiveObject(batch);
            bulletPool.drawActiveObject(batch);
            enemyPool.drawActiveObject(batch);
            healthPoint.draw(batch);
        }
        printInfo();

        batch.end();
    }

    public void printInfo(){
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
//        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmmiter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);

    }

    public void checkCollisions() {
        List<EnemyShip> enemyList = enemyPool.getActiveObjects();
        for (EnemyShip enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.destroy();
                state = State.GAME_OVER;
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                bullet.destroy();
                mainShip.damage(bullet.getDamage());
                if (mainShip.isDestroyed()){
                    state = State.GAME_OVER;
                }
                return;
            }
        }

        for (EnemyShip enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.isDestroyed() || bullet.getOwner() != mainShip) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    bullet.destroy();
                    enemy.damage(bullet.getDamage());
                    if (enemy.isDestroyed()) frags++;
                    return;
                }
            }
        }
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
        mainTheme.dispose();

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
        newGame.btnTouchDown(touch);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        if (newGame.btnTouchUp(touch) && state == State.GAME_OVER) {
            starGame.setScreen(new MenuScreen(starGame));
        }
        return false;
    }



    public void deleteAllDestroyed(){
        bulletPool.freeAllDesrtoyedActiveObject();
        enemyPool.freeAllDesrtoyedActiveObject();
        explosionPool.freeAllDesrtoyedActiveObject();
    }

    private void startNewGame(){
        enemiesEmmiter.setLevel(1);
        state = State.PLAYING;
        frags = 0;
        bulletPool.freeAllActiveObject();
        enemyPool.freeAllActiveObject();
        explosionPool.freeAllActiveObject();
    }
}
