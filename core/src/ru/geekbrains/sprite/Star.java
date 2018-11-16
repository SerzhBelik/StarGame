package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Star(TextureAtlas atlas, String name) {
        super(atlas.findRegion(name));
        float fl = Rnd.nextFloat(1,20);// коэффициент связывающий видимую удаленность звезды и ее скорость движения
        setHeightProportion(0.006f*fl/5);
        this.v.set(Rnd.nextFloat(-0.005f, 0.005f), -0.03f*fl);
//        setHeightProportion(Rnd.nextFloat(0.005f, 0.02f));
//        this.v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.2f, -0.01f));
    }

    public static String getStarName() {
        String name = "star";
        float i = Rnd.nextFloat(0, 5);
        if (i<1) return name;
        else if (i < 2) return name+=1;
        else if (i < 3) return name+=2;
        else if (i < 4) return name+=3;
        else return name+=4;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getHeight());
        pos.set(posX, posY);
    }

    private void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
