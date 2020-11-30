package model.moves;


import model.EndGameException;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.*;
import model.things.NotSoMovable;

import java.util.*;

public class Mover {
    private MoveDecider moveDecider;

    public Mover(int mapWidth, int mapHeight){
        this.moveDecider = new MoveDecider(mapWidth, mapHeight);
    }

    public void moveAll(List<Movable> toMoveObjects, String input) throws EndGameException, IllegalStateException {
        HashMap<Movable, MoveResult> results = this.moveDecider.simulateMove(toMoveObjects, input);
        if(results == null) return;    // -> znaczy, że użytkownik podał input, dzięki któremu doktor wyszedłby poza mapę
        for(Map.Entry<Movable, MoveResult> entry : results.entrySet()){
            if(entry.getValue() == MoveResult.OK){
                Movable movable = entry.getKey();
                if(movable instanceof Doctor) movable.move(Direction.convertInputToDirection(input));
                else movable.move();
            }
        }
        this.printResults(results);
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
