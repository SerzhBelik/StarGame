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
    private int keycode;
    private boolean movement = false;
    private boolean pressed = false;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        backGround = new Texture("background.jpg");
        spaceShip = new Texture("ship.png");
        pos = new Vector2(posX, posY);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (movement) {
            if (pressed){
                getNewPos(keycode);
                course = new Vector2(0,0);
            } else course = getCourse(pos , newPos);
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

    private void getNewPos(int keycode) {
        switch (keycode){
            case 19:
                pos.set(pos.x, pos.y+1);
                break;
            case 20:
                pos.set(pos.x, pos.y-1);
                break;
            case 21:
                pos.set(pos.x-1, pos.y);
                break;
            case 22:
                pos.set(pos.x+1, pos.y);
                break;
        }
        if (pos.x  < 0) pos.x = 0;
        if (HIEGHT - SHIP_HIEGHT - pos.y < 0) pos.y = HIEGHT - SHIP_HIEGHT;
        if (pos.x + SHIP_WIDTH > WIDTH) pos.x = WIDTH - SHIP_WIDTH;
        if (pos.y < 0 ) pos.y = 0;
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
        if (screenX - SHIP_WIDTH/2 < 0) screenX = SHIP_WIDTH/2;
        if (HIEGHT - SHIP_HIEGHT/2 - screenY < 0) screenY = HIEGHT - SHIP_HIEGHT/2;
        if (screenX + SHIP_WIDTH/2 > WIDTH) screenX = WIDTH - SHIP_WIDTH/2;
        if (screenY - SHIP_HIEGHT/2 < 0 ) screenY = SHIP_HIEGHT/2;
        newPos = new Vector2(screenX - SHIP_WIDTH/2, HIEGHT - SHIP_HIEGHT/2 - screenY );
        movement = true;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        this.keycode = keycode;
        movement = true;
        pressed = true;
        System.out.println(keycode);
        switch (keycode){
            case 19:
                newPos = new Vector2(posX, posY+1);
                break;
            case 20:
                newPos = new Vector2(posX, posY-1);
                break;
            case 21:
                newPos = new Vector2(posX-1, posY);
                break;
            case 22:
                newPos = new Vector2(posX+1, posY);
                break;
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
       movement = false;
       pressed = false;
        return false;
    }


}
