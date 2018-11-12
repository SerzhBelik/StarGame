package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;

public class NewGame extends Sprite {
    public NewGame(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.08f);
        pos.set(0, -0.4f);
    }
}
