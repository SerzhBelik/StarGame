package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;

public class Sprite extends Rect {
    protected  float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region){
        if (region == null){
            throw new NullPointerException("region is null  ");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(SpriteBatch batch ){
        batch.draw(
                regions[frame], //actual region
                getLeft(), getBottom(), //draw point
                halfWidth, halfHeight, // rotation point
                getWidth(), getHeight(), // width and height of texture
                scale, scale, // x and y scale
                angle // rotation angel
        );
    }

    public void resize(Rect worldBounds) {

    }
    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {

    }


    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean btnTouchDown(Vector2 touch){
        if(isMe(touch)) {
            setScale((float)(getScale()*0.95));
            return true;
        }
            return false;
    }

    public boolean btnTouchUp(Vector2 touch) {
        if(isMe(touch)) {
            setScale((float)(getScale()/0.95));
            return  true;
        }
        return false;
    }
}
