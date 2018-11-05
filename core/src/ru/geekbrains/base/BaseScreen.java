package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.math.MatrixUtils;
import ru.geekbrains.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;
    private Vector2 touch;


    protected Matrix4 worldTOGL;
    protected Matrix3 screenToWorld;
    protected SpriteBatch batch;
    protected float aspect;
    protected final float WORLD_BOUNDS_MODIFIER = 1f;



    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0,1f,1f);
        this.worldTOGL = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.batch = new SpriteBatch();
        this.touch = new Vector2();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        aspect = width/(float)height;

        worldBounds.setHeight(WORLD_BOUNDS_MODIFIER);
        worldBounds.setWidth(WORLD_BOUNDS_MODIFIER*aspect);
        MatrixUtils.calcTransitionMatrix(worldTOGL, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
        batch.setProjectionMatrix(worldTOGL);

    }

    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    private boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
