package model.moves;

import exceptions.*;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.*;
import model.other.Constants;
import model.things.NotSoMovable;
import model.things.PileOfJunk;
import model.things.PowerUp;

import javax.print.Doc;
import java.util.*;
import java.util.HashMap;

public class MoveDecider {
    private HashMap<Field, MapObject> map = new HashMap<>();
    private HashMap<Field, Movable> move = new HashMap<>();
    private HashMap<Field, Movable> previousMove = new HashMap<>();
    private final WorldMapStack worldStack;

    private final int mapWidth;
    private final int mapHeight;

    private final Random random = new Random();

    public MoveDecider(int mapWidth, int mapHeight, List<MapObject> mapObjects){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.worldStack = new WorldMapStack(mapObjects);
    }

    public HashMap<Movable, MoveResult> simulateMove(List<Movable> movables, Direction input)
            throws EndGameException, IllegalStateException, TeleportationTimesException, UndoException,
            PowerUpException, CantUndoException {
        HashMap<Movable, MoveResult> results = new HashMap<>();
        this.setUpHashMaps();
        if(input == Direction.UNDO){
            Doctor doctor = getDoctorFromMovables(movables);
            if(doctor.canUndo()) {
                doctor.decrementUndoTimes();
                if (doctor.hasPowerUps()) {
                    doctor.removePowerUp();
                }
                this.undo();
            }
            else{
                throw new CantUndoException();
            }
        }
        else if(input == Direction.TELEPORT){
            simulateMoveWithTeleportation(movables, results);
        }
        else{
            results = simulateMoveWithoutTeleportation(movables, input, results);
        }

        if(shouldAddPowerUp()){
            Field powerUpField = randomizePowerUpField();
            map.put(powerUpField, new PowerUp(powerUpField.getX(), powerUpField.getY()));
        }

        HashMap<Field, Movable> moveClone = new HashMap<>(this.move);
        HashMap<Field, MapObject> mapClone = new HashMap<>(this.map);
        this.worldStack.addMoveMap(moveClone);
        this.worldStack.addWorldMap(mapClone);
        this.worldStack.addMove(input);
        return results;
    }

    private void setUpHashMaps(){
        this.move.clear();
        this.previousMove.clear();
        this.map.clear();
        this.map = this.worldStack.getWorldMap();
    }

    private void undo() throws UndoException {
        List<Movable> movables = new ArrayList<>();
        Direction direction = this.worldStack.undo();
        for (Map.Entry<Field, Movable> entry : this.worldStack.getMove().entrySet()) {
            Movable movable = entry.getValue();
            movable.setField(entry.getKey());
            movables.add(movable);
        }
        if(direction == Direction.TELEPORT){
            getDoctorFromMovables(movables).incrementTeleportation();
        }
        this.map = new HashMap<>(this.worldStack.getWorldMap());
        this.move = new HashMap<>(this.worldStack.getMove());
        throw new UndoException(movables);
    }

    private void simulateMoveWithTeleportation(List<Movable> movables, HashMap<Movable,
            MoveResult> results) throws EndGameException, TeleportationTimesException {
        Doctor doctor = this.getDoctorFromMovables(movables);
        this.findTeleportationField(doctor, results);
        for(Movable movable : movables){
            if(!(movable instanceof Doctor)){
                checkCollisions(movable, results, Direction.STAY);
            }
        }
    }

    private void findTeleportationField(Doctor doctor, HashMap<Movable, MoveResult> results) throws TeleportationTimesException {
        Field teleportationField;
        do{
            teleportationField = this.findNewTeleportationFieldIteration(doctor);
        } while(this.getTeleportCondition(teleportationField));
        doctor.setField(teleportationField);
        results.put(doctor, MoveResult.OK);
    }

    private Field findNewTeleportationFieldIteration(Doctor doctor) throws TeleportationTimesException {
        doctor.setNewTeleportationField();
        return doctor.getTeleportationField();
    }

    private boolean getTeleportCondition(Field teleportationField){
        List<Field> dangerFields = teleportationField.getFieldsAround();
        dangerFields.add(teleportationField);
        for(Field dangerField : dangerFields){
            if(map.get(dangerField) != null
                    || previousMove.get(dangerField) != null
                    || move.get(dangerField) != null) return true;
        }
        return false;
    }

    private void checkCollisions(Movable movable, HashMap<Movable, MoveResult> results, Direction input) throws EndGameException {
        checkCollisionWithPieceOfJunkInTheMap(movable, results, input);
        checkCollisionWithPreviousMovables(movable, results, input);
    }

    private HashMap<Movable, MoveResult> simulateMoveWithoutTeleportation(List<Movable> movables, Direction input,
                                          HashMap<Movable, MoveResult> results) throws EndGameException {

        for(Movable movable : movables){
            if(!(movable instanceof Doctor)){
                checkCollisions(movable, results, input);
            }
        }
        for (Movable movable : movables){
            if(movable instanceof Doctor) {
                if (!isInMap(movable, input))
                    return null;
                checkCollisions(movable, results, input);
                break;
            }
        }
        return results;
    }

    private boolean isInMap(Movable movable, Direction input){
        Field calculatedField = calculateField(movable, input);
        return calculatedField.moreThan(new Field(1, 1)) &&
                calculatedField.lessThan(new Field(mapWidth, mapHeight));
    }

    private void checkCollisionWithPieceOfJunkInTheMap(Movable movable,
                                                       HashMap<Movable, MoveResult> results, Direction input)
            throws IllegalStateException, EndGameException {
        Field calculatedField = calculateField(movable, input);
        if(map.get(calculatedField) != null){
            MapObject object = map.get(calculatedField);
            if(object instanceof PileOfJunk) {
                if (movable instanceof Doctor) ((Doctor) movable).die();
                else results.put(movable, MoveResult.COLLISION);
            }
            else if(object instanceof PowerUp && movable instanceof Doctor) {
                ((Doctor) movable).addPowerUp();
                //TODO: remove power up
                map.remove(object.field);
            }
        }
        else{
            results.put(movable, MoveResult.OK);
        }
    }

    private void checkCollisionWithPreviousMovables(Movable movable, HashMap<Movable, MoveResult> results, Direction input)
            throws EndGameException, IllegalStateException {
        Field calculatedField = calculateField(movable, input);

        Movable movableOnFutureField = move.get(calculatedField);
        if(movableOnFutureField != null){
            evaluateNotNullEncounter(movable, results, calculatedField, movableOnFutureField);
        }
        else{
            if(results.get(movable) != MoveResult.COLLISION)
                results.put(movable, MoveResult.OK);
            move.put(calculatedField, movable);
        }
    }

    private void evaluateNotNullEncounter(Movable movable, HashMap<Movable, MoveResult> results,
                                          Field calculatedField, Movable movableOnFutureField) throws EndGameException {
        if(movable instanceof Doctor || movableOnFutureField instanceof Doctor){
            if (movable instanceof Doctor) ((Doctor) movable).die();
            if (movableOnFutureField instanceof Doctor) ((Doctor) movableOnFutureField).die();
        }
        else{
            results.put(movable, MoveResult.COLLISION);
            results.put(movableOnFutureField, MoveResult.COLLISION);
            map.put(calculatedField, new PileOfJunk(calculatedField.getX(), calculatedField.getY()));
        }
    }

    private Field calculateField(Movable movable, Direction input) throws IllegalStateException{
        return movable.calculateNextMove(input);
    }

    public List<MapObject> getWorldMapAsList(){
        List<MapObject> result = new ArrayList<>();
        for(Map.Entry<Field, MapObject> entry : this.map.entrySet()){
            MapObject object = entry.getValue();
            result.add(object);
        }
        return result;
    }

    private void copyMove() {
        previousMove.clear();
        for(Map.Entry<Field, Movable> entry : move.entrySet()){
            previousMove.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * setInitialMap -> sets pilesOfJunk to the map of MoveDecider
     * Use ONLY if you want to add some pile of junks or other not movable elements BEFORE the game starts
     * @param objects - list of NotSoMovable that you want to add before the game starts
     */
    public void setInitialMap(List<NotSoMovable> objects){
        for (NotSoMovable object : objects){
            this.map.put(object.getField(), object);
        }
    }

    public void clearMap(){
        this.worldStack.clear();
        this.map.clear();
        this.move.clear();
        this.previousMove.clear();
    }

    private Doctor getDoctorFromMovables(List<Movable> movables){
        for(Movable movable : movables){
            if(movable instanceof Doctor){
                return (Doctor) movable;
            }
        }
        throw new IllegalStateException("No doctor in movables list!");
    }

    private boolean shouldAddPowerUp(){
        return random.nextInt(101)%(100/Constants.POWER_UP_CHANCE_PERCENT) == 0;
    }

    private Field randomizePowerUpField(){
        Field powerUpField;
        do{
            powerUpField = new Field(random.nextInt(mapWidth + 1), random.nextInt(mapHeight + 1));
        } while(map.get(powerUpField) != null);
        return powerUpField;
    }
}
