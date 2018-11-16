package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Bonus;

public class BonusPool extends SpritesPool {

    private TextureRegion textureRegion;

    public BonusPool (TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(textureRegion);
    }
}
