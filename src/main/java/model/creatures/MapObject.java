package model.creatures;

import model.map.Field;

public class MapObject {
    public Field field;

    public Field getField() {
        return field;
    }

    protected MapObject(Field field){
        this.field = field;
    }
}
