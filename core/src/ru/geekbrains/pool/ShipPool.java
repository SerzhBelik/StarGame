package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.EnemyShip;

public class ShipPool extends SpritesPool<EnemyShip> {
    TextureAtlas mainAtlas = new TextureAtlas("mainAtlas.tpack");
    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(mainAtlas, "enemy0");
    }
}
