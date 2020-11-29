package model.map;

import model.EndGameException;
import model.moves.Mover;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Movable> mapObjects = new ArrayList<>();

    private int width;
    private int height;

    private Mover mover;

    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.mover = new Mover(width, height);
        mapObjects.add(new Doctor(new Field(width/2 + 1, height/2 + 1)));
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

    public List<Movable> getMapObjects() { return mapObjects; }

    public void move(String input){
        System.out.println(input);
        try {
            this.mover.moveAll(mapObjects, input);
        } catch (EndGameException e) {
            e.printStackTrace();   //-> you've lost!
        }
    }
}
