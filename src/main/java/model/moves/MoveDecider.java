package model.moves;

import model.EndGameException;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.*;
import model.things.NotSoMovable;
import model.things.PileOfJunk;

import java.util.*;
import java.util.HashMap;

public class MoveDecider {
    private HashMap<Field, MapObject> map;
    private HashMap<Field, Movable> move;
    private HashMap<Field, Movable> previousMove;

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
            throws EndGameException, IllegalStateException {
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

    private void copyMove() {
        previousMove.clear();
        for(Map.Entry<Field, Movable> entry : move.entrySet()){
            previousMove.put(entry.getKey(), entry.getValue());
        }
    }

    private void simulateMoveWithTeleportation(List<Movable> movables, HashMap<Movable,
            MoveResult> results) throws EndGameException{
        Field teleportationField = new Field(1, 1);
        for(Movable movable : movables){
            results.put(movable, MoveResult.OK);
            if(movable instanceof Doctor){
                teleportationField = ((Doctor) movable).teleportationField;
                while(this.getTeleportCondition(teleportationField)){
                    ((Doctor) movable).setNewTeleportationField();
                    teleportationField = ((Doctor) movable).teleportationField;
                    System.out.println(teleportationField);
                }
            }
            else {      // -> nie chcemy, żeby Doctor się poruszył. Chcemy znaleźć dla niego dobre miejsce
                checkCollisionWithPieceOfJunkInTheMap(movable, results, Direction.STAY);
                checkCollisionWithPreviousMoves(movable, results, Direction.STAY);
            }
        }
        for(Movable movable : movables){
            if(movable instanceof Doctor)
                movable.setField(teleportationField);
        }
    }

    private boolean getTeleportCondition(Field teleportationField){
        List<Field> dangerFields = teleportationField.getFieldsAround();
        dangerFields.add(teleportationField);
        System.out.println("Here");
        for(Field dangerField : dangerFields){
            if(map.get(dangerField) != null || previousMove.get(dangerField) != null) return true;
        }
        return false;
    }

    private HashMap<Movable, MoveResult> simulateMoveWithoutTeleportation(List<Movable> movables, Direction input,
                                                  HashMap<Movable, MoveResult> results)
                                                    throws EndGameException{
        for (Movable movable : movables){
            if(movable instanceof Doctor)
                if(!isInMap(movable, input))
                    return null;
            checkCollisionWithPieceOfJunkInTheMap(movable, results, input);
            checkCollisionWithPreviousMoves(movable, results, input);
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
            throws EndGameException, IllegalStateException {
        Field calculatedField = calculateField(movable, input);
        if(map.get(calculatedField) != null){
            if(movable instanceof Doctor) throw new EndGameException("Game Over");
            else results.put(movable, MoveResult.COLLISION);
        }
        else{
            results.put(movable, MoveResult.OK);    //-> won't collide with piece of junk
        }
    }

    private void checkCollisionWithPreviousMoves(Movable movable, HashMap<Movable, MoveResult> results, Direction input)
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
            throw new EndGameException("The Game is Over");
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
            object.setField(entry.getKey());
            result.add(object);
        }
        return result;
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
}
