package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BonusPool;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;


public class EnemyShip extends Ship {

    private Vector2 v0 = new Vector2();
    Vector2 actionV = new Vector2();
    private BonusPool bonusPool;
    private Bonus bonus;
    private int exp;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, BonusPool bonusPool, Rect worldBounds, Sound shootSound) {
        super(shootSound);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bonusPool = bonusPool;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (this.getTop() <= worldBounds.getTop() && !this.v.equals(actionV)) {
            v.scl(0.95f);
            if (v.y >= actionV.y) {
                this.v.set(actionV);
            }
        }
        reloadTimer +=delta;
        if (reloadTimer >= reloadInterval) {
            shoot(gunType);
            reloadTimer = 0f;
        }

        if (checkBounds()) {
            this.destroy();
            boom();
            System.out.println("Desroyed!");
        }
    }

    private boolean checkBounds() {
        if (this.getBottom() < worldBounds.getBottom()) return true;
        return false;
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
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(v0);
        this.actionV = actionV;
        this.exp = hp*10;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    @Override
    public void destroy() {
        boom();
        hp = 0;
        if (Rnd.nextFloat(0, 1) > 0.8) {
            bonus = (Bonus) bonusPool.obtain();
            bonus.set(pos, v0);
        }
        super.destroy();
    }

    public int getExp() {
        return exp;
    }
}
