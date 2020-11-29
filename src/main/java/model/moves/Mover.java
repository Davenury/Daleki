package model.moves;


import model.EndGameException;
import model.map.Direction;
import model.map.Doctor;
import model.map.MapObject;
import model.map.Movable;

import java.util.*;

public class Mover {
    private MoveDecider moveDecider;

    public Mover(int mapWidth, int mapHeight){
        this.moveDecider = new MoveDecider(mapWidth, mapHeight);
    }

    public void moveAll(List<Movable> toMoveObjects, String input) throws EndGameException {
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
}
