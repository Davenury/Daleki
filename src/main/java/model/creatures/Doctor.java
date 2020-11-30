package model.creatures;

import model.map.Direction;
import model.map.Field;

public class Doctor extends Movable {

    public Doctor(Field field){
        super.field = field;
    }
    @Override
    public String toString(){
        return "Doctor " + super.getField().toString();
    }

    @Override
    public void move(Direction direction){
        super.updateField(super.getField().addAsVector(direction.convertToField()));
    }

    @Override
    public Field calculateNextMove(Direction direction){
        return super.getField().addAsVector(direction.convertToField());
    }
    @Override
    public void moveTo(Field field){
        super.updateField(super.getField().addAsVector(field));
    }

    public void teleport(){
        //TODO
    }

}
