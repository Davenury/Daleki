package model.creatures;

import com.google.inject.Inject;
import diproviders.dimensions.IDimensionsSetter;
import model.map.Direction;
import model.map.Field;

public class Doctor extends Movable {

    private final int worldWidth;

    private final int worldHeight;

    @Inject
    public Doctor(IDimensionsSetter setter){
        this.worldWidth = setter.getWidth();
        this.worldHeight = setter.getHeight();
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
