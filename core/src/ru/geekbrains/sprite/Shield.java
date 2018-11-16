package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Shield extends Sprite {

    public Shield(TextureRegion textureRegion) {
        super(textureRegion);
        setHeightProportion(0.05f);

    }

    public void update(Vector2 pos) {
        this.pos.set(pos);
    }

    public void set(Vector2 pos){
        this.pos.set(pos);
    }
}
