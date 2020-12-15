package model.creatures;

import model.map.Direction;
import model.map.Field;

public abstract class Movable extends MapObject {

    public Movable(Field field){
        super(field);
    }

    public Movable(){
        super(new Field(0, 0));
    }

    protected void updateField(Field field){
        super.field = field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public abstract Field calculateNextMove(Direction direction);
    public abstract void move(Direction direction);
}
