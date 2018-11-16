package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Title extends Sprite {

    public Title(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize (Rect worldBounds){
        setHeightProportion(0.08f);
        pos.set(0, 0.1f);
    }
}
