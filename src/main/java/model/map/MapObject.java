package model.map;

public class MapObject {
    Field field;

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
