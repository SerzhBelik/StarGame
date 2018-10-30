package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;


public class Start_btn extends Sprite {

    public Start_btn(TextureAtlas atlas) {
        super(atlas.findRegion("start"));
        setHeightProportion(0.2f);
        pos.set(-0.2f, -0.4f);
    }

//    public void btnTouchDown(Vector2 touch){
//        if(isMe(touch)) setScale((float)(getScale()*0.95));
//    }
//
//    public void btnTouchUp(Vector2 touch) {
//    }


//public boolean touchDown(Vector2 touch, int pointer) {
//    if(isMe(touch)) System.out.print("push");
//    return false;
//}

}
