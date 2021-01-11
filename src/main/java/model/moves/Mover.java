package model.moves;


import exceptions.*;
import model.creatures.Dalek;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.Direction;
import model.things.NotSoMovable;

import java.util.*;

public class Mover {
    private MoveDecider moveDecider;
    private boolean allDaleksRemoved;

    public Mover(int mapWidth, int mapHeight, List<MapObject> mapObjects){
        this.moveDecider = new MoveDecider(mapWidth, mapHeight, mapObjects);
    }

    public List<MapObject> moveAll(List<Movable> toMoveObjects, Direction input)
            throws EndGameException, IllegalStateException, NextLevelException,
            TeleportationTimesException, CantUndoException, UndoException {
        HashMap<Movable, MoveResult> results;
        try {
            results = this.moveDecider.simulateMove(toMoveObjects, input);
        } catch (UndoException e) {
            return List.copyOf(e.getMovables());
        } catch (CantUndoException e) {
            return List.copyOf(toMoveObjects);
        }
        if(results != null) {
            this.evaluateResults(results, toMoveObjects, input);
        }
        return List.copyOf(toMoveObjects);
    }

    private void evaluateResults(HashMap<Movable, MoveResult> results, List<Movable> toMoveObjects, Direction input)
            throws IllegalStateException, NextLevelException {
        this.allDaleksRemoved = true;
        for (Map.Entry<Movable, MoveResult> entry : results.entrySet()) {
            this.evaluateMovable(entry, toMoveObjects, input);
        }
        if(this.allDaleksRemoved) throw new NextLevelException();
    }

    private void evaluateMovable(Map.Entry<Movable, MoveResult> entry, List<Movable> toMoveObjects, Direction input) {
        Movable movable = entry.getKey();
        if (entry.getValue() == MoveResult.OK) {
            this.evaluateOKResult(movable, input);
            if(movable instanceof Dalek) this.allDaleksRemoved = false;
        } else {
            toMoveObjects.remove(movable);
        }
    }

    private void evaluateOKResult(Movable movable, Direction input) throws IllegalStateException{
        movable.move(input);
    }

    private void printResults(HashMap<Movable, MoveResult> results) {
        for(Map.Entry<Movable, MoveResult> entry : results.entrySet()){
            System.out.println(entry.getKey().toString() + " = " + entry.getValue().toString());
        }
    }

    public List<MapObject> getMapObjects(){
        return this.moveDecider.getWorldMapAsList();
    }

    /**For use cases look in the MoveDecider class*/
    public void setInitialMap(List<NotSoMovable> objectsToAddOnMap){
        this.moveDecider.setInitialMap(objectsToAddOnMap);
    }
}
