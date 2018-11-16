package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Bonus extends Sprite {

    private Vector2 v;

    public Bonus(TextureRegion textureRegion) {
        super(textureRegion);
        setHeightProportion(0.05f);

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }

    public void set(Vector2 pos, Vector2 v){
        this.pos.set(pos);
        this.v = v;
    }
}
