package model.moves;

import exceptions.DoctorDiesException;
import exceptions.EndGameException;
import exceptions.TeleportationTimesException;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.*;
import model.things.NotSoMovable;
import model.things.PileOfJunk;

import java.util.*;
import java.util.HashMap;

public class MoveDecider {
    private final HashMap<Field, MapObject> map;
    private final HashMap<Field, Movable> move;
    private final HashMap<Field, Movable> previousMove;

    private final int mapWidth;
    private final int mapHeight;

    public MoveDecider(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = new HashMap<>();
        this.move = new HashMap<>();
        this.previousMove = new HashMap<>();
    }

    public HashMap<Movable, MoveResult> simulateMove(List<Movable> movables, Direction input)
            throws EndGameException, IllegalStateException, TeleportationTimesException {
        HashMap<Movable, MoveResult> results = new HashMap<>();
        if(input == Direction.TELEPORT){
            simulateMoveWithTeleportation(movables, results);
        }
        else{
            results = simulateMoveWithoutTeleportation(movables, input, results);
        }
        this.copyMove();
        this.move.clear();
        return results;
    }

    private void simulateMoveWithTeleportation(List<Movable> movables, HashMap<Movable,
            MoveResult> results) throws EndGameException, TeleportationTimesException {
        for(Movable movable : movables){
            if(movable instanceof Doctor){
                this.findTeleportationField((Doctor) movable, results);
                break;
            }
        }
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
        for (Movable movable : movables){
            if(movable instanceof Doctor) {
                if (!isInMap(movable, input))
                    return null;
            }
            checkCollisions(movable, results, input);
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
            if(movable instanceof Doctor) ((Doctor) movable).die();
            else results.put(movable, MoveResult.COLLISION);
        }
        else{
            results.put(movable, MoveResult.OK);
        }
    }

    private void checkCollisionWithPreviousMovables(Movable movable, HashMap<Movable, MoveResult> results, Direction input)
            throws EndGameException, IllegalStateException {
        Field calculatedField = calculateField(movable, input);
        Movable movableOnFutureField = move.get(calculatedField);
        Movable movableOnField = move.get(movable.getField());
        if(movableOnFutureField != null){
            evaluateNotNullEncounter(movable, results, calculatedField, movableOnFutureField);
        }
        else if(movableOnField != null){
            evaluateNotNullEncounter(movable, results, calculatedField, movableOnField);
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
        this.move.clear();
        this.map.clear();
        this.previousMove.clear();
    }
}
