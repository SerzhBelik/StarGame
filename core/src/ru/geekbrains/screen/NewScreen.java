package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;

public class NewScreen  extends BaseScreen {
    private Texture backGround;
    private Texture spaceShip;
//    private float speedModifier = super.WORLD_BOUNDS_MODIFIER/200;
//    private float shipHeight = super.WORLD_BOUNDS_MODIFIER/6;
//    private float shipWidth = super.WORLD_BOUNDS_MODIFIER/6;

    private Vector2 pos;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 buf;
    private Sprite sprite;

    @Override
    public void show() {
        super.show();
//        batch = new SpriteBatch();
        backGround = new Texture("background.jpg");
        spaceShip = new Texture("ship.png");
        pos = new Vector2();
        touch = new Vector2();
        v = new Vector2();
        buf = new Vector2();
//        pos.set(-shipWidth/2, -super.WORLD_BOUNDS_MODIFIER/2);
        sprite = new Sprite(new TextureRegion(spaceShip));
//        sprite.setHeight(shipHeight);
//        sprite.setWidth(shipWidth);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        buf.set(touch);
        if (buf.sub(pos).len() > v.len()){
            pos.add(v);
        } else {
            pos.set(touch);
        }

        batch.begin();
        sprite.draw(batch);
//        batch.draw(backGround, -super.WORLD_BOUNDS_MODIFIER/2*super.aspect,-super.WORLD_BOUNDS_MODIFIER/2,
//                super.WORLD_BOUNDS_MODIFIER*super.aspect, super.WORLD_BOUNDS_MODIFIER);
//        batch.draw(backGround, -21f,-21f, 42f, 42f);
//        batch.draw(spaceShip, pos.x, pos.y, shipWidth, shipHeight);
        batch.end();

    }


    @Override
    public void dispose() {
        batch.dispose();
        backGround.dispose();
        spaceShip.dispose();;
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
//        v.set(touch.cpy().sub(pos).nor().scl(speedModifier));
        return false;
    }

}
