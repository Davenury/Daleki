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
    private HashMap<Field, MapObject> map= new HashMap<>();
    private HashMap<Field, Movable> move = new HashMap<>();

    private final int mapWidth;
    private final int mapHeight;

    public MoveDecider(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        //this.map.put(new Field(5,5), new PileOfJunk(5, 5));// -> uncomment to put a pile of Junk
    }

    public HashMap<Movable, MoveResult> simulateMove(List<Movable> movables, String input)
            throws EndGameException, IllegalStateException {
        HashMap<Movable, MoveResult> results = new HashMap<>();
        for (Movable movable : movables){
            if(movable instanceof Doctor)
                if(!isInMap(movable, input))
                    return null;
            checkCollisionWithPieceOfJunkInTheMap(movable, results, input);
            checkCollisionWithPreviousMoves(movable, results, input);
        }
        clearMove();
        return results;
    }

    private void checkCollisionWithPieceOfJunkInTheMap(Movable movable,
                                                       HashMap<Movable, MoveResult> results, String input)
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

    private void checkCollisionWithPreviousMoves(Movable movable, HashMap<Movable, MoveResult> results, String input)
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
            results.put(movable, MoveResult.OK);
            move.put(calculatedField, movable);
        }
    }

    private void evaluateNotNullEncounter(Movable movable, HashMap<Movable, MoveResult> results,
                                          Field calculatedField, Movable movableOnFutureField) throws EndGameException {
        if(movable instanceof Doctor || movableOnFutureField instanceof Doctor){
            throw new EndGameException("Game Over");
        }
        else{
            results.put(movable, MoveResult.COLLISION);
            results.put(movableOnFutureField, MoveResult.COLLISION);
            map.put(calculatedField, new PileOfJunk(calculatedField.getX(), calculatedField.getY()));
        }
    }

    private Field calculateField(Movable movable, String input) throws IllegalStateException{
        return movable.calculateNextMove(Direction.convertInputToDirection(input));
    }

    private boolean isInMap(Movable movable, String input){
        Field calculatedField = calculateField(movable, input);
        return calculatedField.moreThan(new Field(1, 1)) &&
                calculatedField.lessThan(new Field(mapWidth, mapHeight));
    }

    private void clearMove(){
        move.clear();
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
