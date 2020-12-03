package model.creatures;

import com.google.inject.Inject;
import diproviders.dimensions.IDimensionsSetter;
import model.map.Direction;
import model.map.Field;
import view.input.InputParser;

import java.util.Random;

public class Doctor extends Movable {

    private final int worldWidth;

    private final int worldHeight;

    private Random random;
    public Field teleportationField;

    @Inject
    private Doctor(IDimensionsSetter setter){
        this.worldWidth = setter.getWidth();
        this.worldHeight = setter.getHeight();
        this.random = new Random();
        InputParser.subscribeToInputParser(this::setNewTeleportationField);
    }

    @Override
    public String toString(){
        return "Doctor " + super.getField().toString();
    }

    @Override
    public void move(Direction direction){
        if(direction != Direction.TELEPORT)
            super.updateField(super.getField().addAsVector(direction.convertToField()));
        else
            super.updateField(teleportationField);
    }

    @Override
    public Field calculateNextMove(Direction direction){
        if(direction != Direction.TELEPORT)
            return super.getField().addAsVector(direction.convertToField());
        return teleportationField;
    }
    @Override
    public void moveTo(Field field){
        super.updateField(super.getField().addAsVector(field));
    }

    public void setNewTeleportationField(){
        //TODO
        teleportationField = new Field(this.random.nextInt(11) + 1, this.random.nextInt(11) + 1);
    }

}
