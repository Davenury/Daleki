package model.creatures;

import model.creatures.MapObject;
import model.map.Direction;
import model.map.Field;

public abstract class Movable extends MapObject {

    protected void updateField(Field field){
        super.field = field;
    }

    public Field calculateNextMove(){
        return null;
    };

    public Field calculateNextMove(Direction direction){
        return null;
    };
    public abstract void move(Direction direction);
    public void moveTo(Field field){};
}
