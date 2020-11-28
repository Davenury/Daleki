package model.map;

public abstract class Movable extends MapObject {

    protected void updateField(Field field){
        super.field = field;
    }

    void move(Direction direction){};
    void move(Field field){};
    void move(){};
}
