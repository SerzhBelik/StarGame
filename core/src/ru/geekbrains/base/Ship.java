package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;

public abstract class Ship extends Sprite {

    protected Vector2 v = new Vector2();
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Rect worldBounds;

    protected Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer;

    protected int hp;
    protected TextureRegion bulletRegion;

    protected int gunType = 1;

    private Sound shootSound;

    public Ship(TextureRegion region, int rows, int cols, int frames, Sound shootSound) {
        super(region, rows, cols, frames);
        this.shootSound = shootSound;
    }

    public Ship(Sound shootSound) {
        this.shootSound = shootSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }


    protected void shoot(int gunType) {
        for (int i = 0; i < gunType; i++){
            bulletV.x = -0.15f * (gunType - 1)/2 + 0.15f * i;
            Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
    }
        shootSound.play();
    }

    protected void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            destroy();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getGunType() {
        return gunType;
    }

    public void setGunType(int gunType) {
        this.gunType = gunType;
    }
}