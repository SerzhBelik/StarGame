package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class NewScreen  extends BaseScreen {
    private SpriteBatch batch;
    private Texture backGround;
    private Texture spaceShip;
    private final float SPEED_MODIFIER = 2;
    private Vector2 pos;
    private Vector2 course;
    private Vector2 v;
    private Vector2 newPos = null;
    private float posY = 0;
    private float posX = 280;
    private final int WIDTH = 640;
    private final int HIEGHT = 480;
    private final int SHIP_WIDTH = 80;
    private final int SHIP_HIEGHT = 112;
    private boolean movement = false;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        backGround = new Texture("background.jpg");
        spaceShip = new Texture("ship.png");
        pos = new Vector2(posX,posY);
//        v = new Vector2(0.5f,0.3f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (movement) {
            course = getCourse(pos , newPos);
            System.out.println(course.x + " " + course.y);
        }
        batch.begin();
        batch.draw(backGround, 0,0);
        batch.draw(spaceShip, pos.x, pos.y);
        batch.end();
        if (movement) {
            pos.add(course);
            if(newPos.cpy().sub(pos).len()<1){
                movement = false;
            }
        }
    }

    private Vector2 getCourse(Vector2 pos, Vector2 newPos) {

        return newPos.cpy().sub(pos).nor().scl(SPEED_MODIFIER);
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
//        if(newPos == null) {
            newPos = new Vector2(screenX - SHIP_WIDTH/2, HIEGHT - SHIP_HIEGHT/2 - screenY );
//        }
        movement = true;
        System.out.println(screenX + " " + screenY);
        System.out.println(pos.x + " " + pos.y);

        return super.touchDown(screenX,screenY, pointer, button);
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
