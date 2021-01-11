package model.moves;

import model.creatures.MapObject;
import model.creatures.Movable;
import model.map.Direction;
import model.map.Field;
import model.things.NotSoMovable;

import java.util.*;

public class WorldMapStack {

    public List<HashMap<Field, MapObject>> worldMapStack;
    public List<HashMap<Field, Movable>> moveStack;
    public List<Direction> moves;

    public WorldMapStack(List<MapObject> mapObjects){
        this.worldMapStack = new ArrayList<>();
        this.moveStack = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.makeFirst(mapObjects);
    }

    private void makeFirst(List<MapObject> mapObjects) {
        HashMap<Field, Movable> move = new HashMap<>();
        HashMap<Field, MapObject> map = new HashMap<>();
        mapObjects.stream()
                .filter(object -> object instanceof Movable)
                .map(object -> (Movable) object)
                .forEach(movable -> move.put(movable.getField(), movable));
        mapObjects.stream()
                .filter(object -> object instanceof NotSoMovable)
                .map(object -> (NotSoMovable) object)
                .forEach(object -> map.put(object.getField(), object));
        moveStack.add(move);
        worldMapStack.add(map);
    }

    public void addWorldMap(HashMap<Field, MapObject> worldMap){
        this.worldMapStack.add(worldMap);
    }

    public void addMoveMap(HashMap<Field, Movable> move){
        this.moveStack.add(move);
    }

    public void addMove(Direction direction){
        this.moves.add(direction);
        System.out.println(this.moves);
    }

    public Direction undo(){
        Direction direction = Direction.STAY;
        if(this.worldMapStack.size() > 0){
            this.worldMapStack.remove(this.worldMapStack.size() - 1);
        }
        if(this.moveStack.size() > 1){
            System.out.println("Here");
            this.moveStack.remove(this.moveStack.size() - 1);
        }
        if(this.moves.size() > 0){
            direction = this.moves.get(this.moves.size() - 1);
            this.moves.remove(this.moves.size() - 1);
        }
        return direction;
    }

    public HashMap<Field, MapObject> getWorldMap(){
        return this.worldMapStack.size() > 0 ? this.worldMapStack.get(this.worldMapStack.size() - 1) : new HashMap<>();
    }

    public HashMap<Field, Movable> getMove(){
        System.out.println(this.moveStack.size());
        return this.moveStack.size() > 0 ? this.moveStack.get(this.moveStack.size() - 1) : new HashMap<>();
    }

    public void clear(){
        this.worldMapStack.clear();
        this.moveStack.clear();
    }
}
