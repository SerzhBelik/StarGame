package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {
    private static final int STAR_COUNT = 256;
    private Background background;
    private Texture bgTexture;
    private TextureAtlas textureAtlas;
    private Star[] stars   ;
//    private Texture backGround;
//    private Texture spaceShip;
//    private float speedModifier = super.WORLD_BOUNDS_MODIFIER/200;
//    private float shipHeight = super.WORLD_BOUNDS_MODIFIER/6;
//    private float shipWidth = super.WORLD_BOUNDS_MODIFIER/6;

//    private Vector2 pos;
//    private Vector2 touch;
//    private Vector2 v;
//    private Vector2 buf;
//    private Sprite sprite;

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("atlas.atlas");
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas);
        }
//        batch = new SpriteBatch();
//        backGround = new Texture("background.jpg");
//        spaceShip = new Texture("ship.png");
//        pos = new Vector2();
//        touch = new Vector2();
//        v = new Vector2();
//        buf = new Vector2();
//        pos.set(-shipWidth/2, -super.WORLD_BOUNDS_MODIFIER/2);
//        sprite = new Sprite(new TextureRegion(spaceShip));
//        sprite.setHeight(shipHeight);
//        sprite.setWidth(shipWidth);

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
            batch.end();

//        buf.set(touch);
//        if (buf.sub(pos).len() > v.len()){
//            pos.add(v);
//        } else {
//            pos.set(touch);
//        }

//        batch.begin();
//        sprite.draw(batch);
//        batch.draw(backGround, -super.WORLD_BOUNDS_MODIFIER/2*super.aspect,-super.WORLD_BOUNDS_MODIFIER/2,
//                super.WORLD_BOUNDS_MODIFIER*super.aspect, super.WORLD_BOUNDS_MODIFIER);
//        batch.draw(backGround, -21f,-21f, 42f, 42f);
//        batch.draw(spaceShip, pos.x, pos.y, shipWidth, shipHeight);
//        batch.end();

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
//        batch.dispose();
//        backGround.dispose();
//        spaceShip.dispose();;
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
//        this.touch = touch;
//        v.set(touch.cpy().sub(pos).nor().scl(speedModifier));
        return false;
    }
}
