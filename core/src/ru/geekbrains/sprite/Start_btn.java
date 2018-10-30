package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.Sprite;


public class Start_btn extends Sprite {

    public Start_btn(TextureAtlas atlas) {
        super(atlas.findRegion("start"));
        setHeightProportion(0.2f);
        pos.set(-0.2f, -0.4f);
    }

}
