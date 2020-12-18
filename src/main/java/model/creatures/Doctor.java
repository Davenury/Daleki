package model.creatures;

import com.google.inject.Inject;
import diproviders.dimensions.IDimensionsSetter;
import exceptions.TeleportationTimesException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.map.Direction;
import model.map.Field;
import model.other.Constants;
import view.input.InputParser;

import java.util.Random;

public class Doctor extends Movable {

    private final int worldWidth;

    private final int worldHeight;

    private final Random random;
    private Field teleportationField;
    private IntegerProperty teleportationTimes = new SimpleIntegerProperty(Constants.TELEPORTATION_TIMES);

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
        if(direction != Direction.TELEPORT) {
            super.updateField(super.getField().addAsVector(direction.convertToField()));
        }
        else {
            super.updateField(teleportationField);
            this.teleportationTimes.set(this.teleportationTimes.get() - 1);
        }
    }

    @Override
    public Field calculateNextMove(Direction direction){
        if(direction != Direction.TELEPORT) {
            return super.getField().addAsVector(direction.convertToField());
        }
        return teleportationField;
    }

    public void moveTo(Field field){
        super.updateField(super.getField().addAsVector(field));
    }

    public void setNewTeleportationField() throws TeleportationTimesException{
        if(this.teleportationTimes.get() <= 0) {
            throw new TeleportationTimesException();
        }
        teleportationField = new Field(this.random.nextInt(worldWidth) + 1, this.random.nextInt(worldHeight) + 1);
    }

    public IntegerProperty teleportationTimesProperty(){
        return teleportationTimes;
    }

    public void setTeleportationTimesProperty(IntegerProperty teleportationTimes){
        this.teleportationTimes = teleportationTimes;
    }

    public void resetTeleportationTimes(){
        this.teleportationTimes.set(Constants.TELEPORTATION_TIMES);
    }

    public Field getTeleportationField(){
        return this.teleportationField;
    }
}
