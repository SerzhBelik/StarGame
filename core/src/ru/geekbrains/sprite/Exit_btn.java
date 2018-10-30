package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Exit_btn extends Sprite {

    public Exit_btn(TextureAtlas atlas) {
        super(atlas.findRegion("exit"));
        setHeightProportion(0.16f);
        pos.set(0.2f, -0.4f);
    }



//    public boolean touchDown(Vector2 touch, int pointer) {
//        if(isMe(touch)) System.out.print("push");
//        return false;
//    }

}
