package model.creatures;

import model.map.Field;

public class MapObject {
    public Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isMovable(){
        return this instanceof Movable;
    }
}
