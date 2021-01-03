package model.creatures;

import com.google.inject.Inject;
import exceptions.EndGameException;
import exceptions.TeleportationTimesException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.map.Direction;
import model.map.Field;
import model.other.Constants;
import view.input.InputParser;

import java.util.Random;

public class Doctor extends Movable {

    private int worldWidth;
    private int worldHeight;

    private final Random random;
    private Field teleportationField;
    private IntegerProperty teleportationTimes = new SimpleIntegerProperty(Constants.TELEPORTATION_TIMES);
    private IntegerProperty spareLives = new SimpleIntegerProperty(Constants.SPARE_LIVES);

    private Boolean diedInThisRound = false;
    private Boolean diedInPrevRound = false;

    private Boolean teleportationPossible = true;

    @Inject
    public Doctor(Field field, int worldWidth, int worldHeight){
        this.field = field; //inherited from MapObjects
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.random = new Random();
        InputParser.subscribeToInputParser(this::setNewTeleportationField);
    }

    @Override
    public String toString(){
        return "Doctor " + super.getField().toString();
    }

    @Override
    public void move(Direction direction){
        if (!diedInThisRound) {
            if (direction != Direction.TELEPORT) {
                super.updateField(super.getField().addAsVector(direction.convertToField()));
            } else {
                super.updateField(teleportationField);
                this.teleportationTimes.set(this.teleportationTimes.get() - 1);
            }
        }
        diedInPrevRound = diedInThisRound;
        diedInThisRound = false;
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

    //Teleportation Times Property
    public IntegerProperty teleportationTimesProperty(){
        return teleportationTimes;
    }

    public void setTeleportationTimesProperty(IntegerProperty teleportationTimes){
        this.teleportationTimes = teleportationTimes;
    }
    public void resetTeleportationTimes(){
        this.teleportationTimes.set(Constants.TELEPORTATION_TIMES);
    }

    public void incrementTeleportation(){
        this.teleportationTimes.set(this.teleportationTimes.get() + 1);
        System.out.println("Teleport " + this.teleportationTimes.get());
    }

    //Spare Lives Property
    public IntegerProperty spareLivesProperty(){
        return spareLives;
    }
    public void resetSpareLives() {
        this.spareLives.set(Constants.SPARE_LIVES);
    }

    public void setSpareLivesProperty(IntegerProperty teleportationTimes){
        this.teleportationTimes = teleportationTimes;
    }

    public void die() throws EndGameException {
        System.out.println(teleportationField);
        if (!diedInThisRound){//Lose one life in case of many Daleks
            this.spareLives.set(this.spareLives.get() - 1);
            if(this.spareLives.get() <= 0) {
                throw new EndGameException();
            }
            //teleport without loosing any teleportation
            teleportationField = new Field(this.random.nextInt(worldWidth) + 1, this.random.nextInt(worldHeight) + 1);
            super.updateField(teleportationField);
        }
        diedInPrevRound = diedInThisRound;
        diedInThisRound = true;

    }
    public Field getTeleportationField(){
        return this.teleportationField;
    }

    public Boolean getDiedInPrevRound(){return this.diedInPrevRound;}



}
