package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ShipPool;

public class EnemyShip extends Sprite {
//    private ShipPool shipPool;
    private TextureAtlas atlas;
    private Vector2 v = new Vector2();
    public static Rect worldBounds;

    public EnemyShip(TextureAtlas atlas, String name) {
        super(atlas.findRegion(name), 1, 2 , 2);
        this.atlas = atlas;
        setHeightProportion(0.15f);

    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if (this.getTop() < worldBounds.getBottom()){
            destroy();
        }
    }

    public void set(
            Vector2 pos0,
            Vector2 v0
    ){
        this.pos.set(pos0);
        this.v.set(v0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }
}
