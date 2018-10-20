package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class NewScreen  extends BaseScreen {
    SpriteBatch batch;
    Texture backGround;
    Texture spaceShip;
    private Vector2 pos;
    private Vector2 v;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        backGround = new Texture("background.jpg");
        spaceShip = new Texture("ship.png");
        pos = new Vector2(0,0);
        v = new Vector2(0.5f,0.3f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backGround, 0,0);
        batch.draw(spaceShip, pos.x, pos.y);
        batch.end();
        pos.add(v);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backGround.dispose();
        spaceShip.dispose();;
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

//    @Override
//    public void create () {
//        batch = new SpriteBatch();
//        backGround = new Texture("background.jpg");
//        spaceShip = new Texture("ship.png");
//    }
//
//    @Override
//    public void render () {
//        Gdx.gl.glClearColor(0.123f, 0.53f, 0.9f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(backGround, 0, 0);
//        batch.draw(spaceShip, 10, 10);
//        batch.end();
//    }

}
