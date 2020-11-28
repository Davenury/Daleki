package model.map;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Movable> mapObjects = new ArrayList<>();

    private int width;
    private int height;

    public World(int width, int height){
        this.width = width;
        this.height = height;

        mapObjects.add(new Doctor(new Field(width/2 + 1, height/2 + 1)));
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

    public List<Movable> getMapObjects() { return mapObjects; }

    public void move(String input){
        System.out.println(input);
    }
}
