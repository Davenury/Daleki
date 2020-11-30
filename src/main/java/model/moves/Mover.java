package model.moves;


import model.EndGameException;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.*;
import model.things.NotSoMovable;

import java.util.*;
import java.util.stream.Collectors;

public class Mover {
    private MoveDecider moveDecider;

    public Mover(int mapWidth, int mapHeight){
        this.moveDecider = new MoveDecider(mapWidth, mapHeight);
    }

    public List<MapObject> moveAll(List<Movable> toMoveObjects, String input) throws EndGameException, IllegalStateException {
        HashMap<Movable, MoveResult> results = this.moveDecider.simulateMove(toMoveObjects, input);
        if(results != null) {
            this.evaluateResults(results, toMoveObjects, input);
        }
        return toMoveObjects.stream()
                .map(it -> (MapObject) it)
                .collect(Collectors.toList());
    }

    private void evaluateResults(HashMap<Movable, MoveResult> results, List<Movable> toMoveObjects, String input) {
        for (Map.Entry<Movable, MoveResult> entry : results.entrySet()) {
            this.evaluateMovable(entry, toMoveObjects, input);
        }
        this.printResults(results);
    }

    private void evaluateMovable(Map.Entry<Movable, MoveResult> entry, List<Movable> toMoveObjects, String input) {
        Movable movable = entry.getKey();
        if (entry.getValue() == MoveResult.OK) {
            this.evaluateOKResult(movable, input);
        } else {
            toMoveObjects.remove(movable);
        }
    }

    private void evaluateOKResult(Movable movable, String input) {
        movable.move(Direction.convertInputToDirection(input));
    }


    private void printResults(HashMap<Movable, MoveResult> results) {
        for(Map.Entry<Movable, MoveResult> entry : results.entrySet()){
            System.out.println(entry.getKey().toString() + " = " + entry.getValue().toString());
        }
    }

    public void teleport(){
        //TODO
    }

    public List<MapObject> getMapObjects(){
        return this.moveDecider.getWorldMapAsList();
    }

    /**For use cases look in the MoveDecider class*/
    public void setInitialMap(List<NotSoMovable> objectsToAddOnMap){
        this.moveDecider.setInitialMap(objectsToAddOnMap);
    }
}
