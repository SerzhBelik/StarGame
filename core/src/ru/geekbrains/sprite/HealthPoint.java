package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;

public class HealthPoint extends Sprite {

    private final float BASE_HP_WIDTH = 0.6f;
    private float hpWidth = BASE_HP_WIDTH;

    public HealthPoint(TextureRegion textureRegion) {
        super(textureRegion);
        setHeightProportion(0.01f);
        setWidth(BASE_HP_WIDTH);
        setLeft(-0.3f);
        setBottom(-0.48f);
    }

    public void update(float delta, int hp) {
        super.update(delta);
        setWidth(BASE_HP_WIDTH / 100 * hp);
        setLeft(-0.3f);

    }
}
