package model.map;

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
    public void move(Direction direction){};
    public void moveTo(Field field){};
    public void move(){};
}
