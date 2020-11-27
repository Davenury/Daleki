package model.map;

import java.util.List;

public class World {

    private List<Movable> mapObjects;

    private int width;
    private int height;

    public World(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

}
