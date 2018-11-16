package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;

    private Vector2 v0 = new Vector2(0.5f, 0);

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Shield shield;
    private boolean isShield;

    private float gunTimer = 0;
    private float shieldTimer = 0;
//    private float gunInterval = 10;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2, shootSound);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletV.set(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.bulletDamage = 1;
        this.reloadInterval = 0.2f;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.hp = 100;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        checkBounds();
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (gunTimer > 0) gunTimer -= delta;
        else setGunType(1);

        if (shieldTimer > 0) shieldTimer -= delta;
        else setShield(false);

        if (reloadTimer >= reloadInterval) {
            shoot(gunType);
            reloadTimer = 0f;
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.print("x: " + touch.x + " y: " + touch.y);
        if (touch.x > worldBounds.pos.x){
            if(rightPointer != INVALID_POINTER)return false;
            rightPointer = pointer;
            moveRight();
        } else {
            if(leftPointer != INVALID_POINTER)return false;
            leftPointer = pointer;
            moveLeft();
        }
        return  false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return super.touchUp(touch, pointer);
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;

        }
        return true;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight){
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft){
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight(){
        v.set(v0);
    }

    private void moveLeft(){
        v.set(v0).rotate(180);
    }

    private void stop(){
        v.setZero();
    }

    private boolean checkBounds (){
        if (this.getLeft() < worldBounds.getLeft()){
            this.stop();
            this.setLeft(worldBounds.getLeft());
        }

        if (this.getRight() > worldBounds.getRight()){
            this.stop();
            this.setRight(worldBounds.getRight());
        }
        return false;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() <getBottom()
        );
    }

    @Override
    public void destroy() {
        boom();
        hp = 0;
        super.destroy();
    }


    public void setBonus() {
        float bonusType = Rnd.nextFloat(0, 1);
        if (bonusType < 0.2){
            heal();
        } else if (bonusType < 0.5){
            setGunType(3);
            gunTimer = 10;
        } else if (bonusType < 0.7){
            setGunType(5);
            gunTimer = 10;
        } else {
            enabledShield();
            shieldTimer = 10;
        }
    }

    private void enabledShield() {
        isShield = true;
    }


    private void heal() {
        if (hp + 20 < 100) hp += 20;
        else hp = 100;
        
    }

    public boolean isShield() {
        return isShield;
    }

    public void setShield(boolean shield) {
        isShield = shield;
    }
}
