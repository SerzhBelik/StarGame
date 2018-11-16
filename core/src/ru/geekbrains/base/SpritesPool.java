package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool <T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<T>();

    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()){
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
//        System.out.println("active/free:" + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    public void drawActiveObject(SpriteBatch batch){
        for (int i = 0; i < activeObjects.size(); i++){
            Sprite sprite = activeObjects.get(i);
            if (!sprite.isDestroyed){
                sprite.draw(batch);
            }
        }
    }

    public void updateActiveObject(float delta){
        for (int i = 0; i < activeObjects.size(); i++){
            Sprite sprite = activeObjects.get(i);
            if (!sprite.isDestroyed){
                sprite.update(delta);
            }
        }
    }

    private void free(T object){
        if (activeObjects.remove(object)){
            freeObjects.add(object);
        }
//        System.out.println(this.getClass().getSimpleName() + "active/free:" + activeObjects.size() + "/" + freeObjects.size());

    }

    public void freeAllDesrtoyedActiveObject(){
        for (int i = 0; i < activeObjects.size(); i++){
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed){
                free(sprite);
                i--;
                sprite.flushDesrtoy();
            }
        }
    }

    public void freeAllActiveObject(){
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }
}
