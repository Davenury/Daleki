package model.moves;

import javafx.util.Pair;
import model.EndGameException;
import model.map.*;

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
    }

    public HashMap<Movable, MoveResult> simulateMove(List<Movable> movables, String input) throws EndGameException {
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
            throws EndGameException {
        Field calculatedField = calculateFieldDependsOnMovable(movable, input);
        if(map.get(calculatedField) != null){
            if(movable instanceof Doctor) throw new EndGameException("Game Over");
            else results.put(movable, MoveResult.COLLISION);
        }
        else{
            results.put(movable, MoveResult.OK);    //-> won't collide with piece of junk
        }
    }

    private void checkCollisionWithPreviousMoves(Movable movable, HashMap<Movable, MoveResult> results, String input)
            throws EndGameException {
        Field calculatedField = calculateFieldDependsOnMovable(movable, input);
        Movable movableOnField = move.get(calculatedField);
        if(movableOnField != null){
            if(movable instanceof Doctor){
                throw new EndGameException("Game Over");}
            else{
                results.put(movable, MoveResult.COLLISION);
                results.put(movableOnField, MoveResult.COLLISION);
                map.put(calculatedField, new PileOfJunk());
            }
        }
        else{
            results.put(movable, MoveResult.OK);
            move.put(calculatedField, movable);
        }
    }

    private Field calculateFieldDependsOnMovable(Movable movable, String input){
        Field calculatedField;
        if(movable instanceof Doctor)
            calculatedField = movable.calculateNextMove(Direction.convertInputToDirection(input));
        else
            calculatedField = movable.calculateNextMove();
        return calculatedField;
    }

    private boolean isInMap(Movable movable, String input){
        Field calculatedField = calculateFieldDependsOnMovable(movable, input);
        return calculatedField.moreThan(new Field(0, 0)) &&
                calculatedField.lessThan(new Field(mapWidth, mapHeight));
    }

    private void clearMove(){
        move.clear();
    }

    private void clearMap(){
        this.clearMove();
        map.clear();
    }
}
