package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.utils.Regions;

public class Sprite extends Rect {
    protected  float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    protected boolean isPressed;
    protected boolean isDestroyed;

    public Sprite(){

    }

    public Sprite(TextureRegion region){
        if (region == null){
            throw new NullPointerException("region is null  ");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite (TextureRegion region, int rows, int cols, int frames){
        this.regions = Regions.split(region, rows, cols, frames);
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
            isPressed = true;
            System.out.print(touch.x + " " + touch.y);
            return true;
        }
            return false;
    }

    public boolean btnTouchUp(Vector2 touch) {
        if (isPressed)setScale((float)(getScale()/0.95));
        System.out.print(touch.x + " " + touch.y);
        if(isMe(touch) && isPressed){
            isPressed = false;
            System.out.print("dfasdf");
            return  true;
        }
        isPressed = false;
        return false;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public void flushDesrtoy(){
        isDestroyed = false;
    }
}
