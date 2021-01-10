package model.creatures;

import com.google.inject.Inject;
import exceptions.EndGameException;
import exceptions.PowerUpException;
import exceptions.TeleportationTimesException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.map.Direction;
import model.map.Field;
import model.other.Constants;
import view.input.InputParser;

import java.util.Random;
import java.util.concurrent.locks.Condition;

public class Doctor extends Movable {

    private int worldWidth;
    private int worldHeight;

    private final Random random;
    private Field teleportationField;
    private IntegerProperty teleportationTimes = new SimpleIntegerProperty(Constants.TELEPORTATION_TIMES);
    private IntegerProperty spareLives = new SimpleIntegerProperty(Constants.SPARE_LIVES);
    private IntegerProperty powerUps = new SimpleIntegerProperty(Constants.POWER_UPS);
    private IntegerProperty undoTimes = new SimpleIntegerProperty(Constants.UNDO_TIMES);

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

    //Undo
    public IntegerProperty undoTimesProperty(){return this.undoTimes;}

    public void setUndoTimesProperty(IntegerProperty undoTimes){
        this.undoTimes = undoTimes;
    }

    public void resetUndoTimes(){
        this.undoTimes.set(Constants.UNDO_TIMES);
    }

    public void incrementUndoTimes(){
        this.undoTimes.set(this.undoTimes.get() + 1);
    }

    public void decrementUndoTimes(){
        this.undoTimes.set(this.undoTimes.get() - 1);
    }

    public boolean canUndo(){
        return this.undoTimes.get() > 0;
    }

    //Spare Lives Property
    public IntegerProperty spareLivesProperty(){
        return spareLives;
    }

    public void resetSpareLives() {
        this.spareLives.set(Constants.SPARE_LIVES);
    }

    public void setSpareLivesProperty(IntegerProperty spareLives){
        this.spareLives = spareLives;
    }

    public IntegerProperty powerUpsProperty() { return powerUps; }

    public void resetPowerUps() { this.powerUps.set(Constants.POWER_UPS); }

    public void setPowerUpsProperty(IntegerProperty powerUps){
        this.powerUps = powerUps;
    }

    public void addPowerUp(){
        if(random.nextInt(10) % 2 == 0){
            this.incrementTeleportation();
        }
        else{
            this.incrementUndoTimes();
        }
    }

    public void removePowerUp() throws PowerUpException {
        if(powerUps.get() == 0){
            throw new PowerUpException();
        }
        powerUps.setValue(powerUps.getValue() - 1);
    }

    public boolean hasPowerUps(){
        return this.powerUps.get() > 0;
    }

    public void die() throws EndGameException {
        System.out.println(teleportationField);
        if (!diedInThisRound){//Lose one life in case of many Daleks
            this.spareLives.set(this.spareLives.get() - 1);
            this.resetPowerUps();
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
