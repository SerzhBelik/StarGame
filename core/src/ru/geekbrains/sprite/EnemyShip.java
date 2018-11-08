package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;


public class EnemyShip extends Ship {

    private Vector2 v0 = new Vector2();
    Vector2 actionV = new Vector2();

    public EnemyShip(BulletPool bulletPool, Rect worldBounds, Sound shootSound) {
        super(shootSound);
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (this.getTop() <= worldBounds.getHalfHeight() && !this.v.equals(actionV)) {
            v.scl(0.95f);
            if (v.y >= actionV.y) {
                this.v.set(actionV);
            }
//            System.out.println(this.getTop());
//            System.out.println(worldBounds.getHeight());
        }
        reloadTimer +=delta;
        if (reloadTimer >= reloadInterval) {
            shoot();
            reloadTimer = 0f;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp,
            Vector2 actionV
    ) {
        this.regions = regions;
        this.v0.set(v0);
//        System.out.print(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(v0);
        this.actionV = actionV;
    }
}
