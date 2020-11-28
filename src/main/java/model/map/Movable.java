package model.map;

public abstract class Movable extends MapObject {

    protected void updateField(Field field){
        super.field = field;
    }

    public Field calculateNextMove(){
        return null;
    };

    public Field calculateNextMove(Field field){
        return null;
    };
    void move(Direction direction){};
    void move(Field field){};
    void move(){};
}
