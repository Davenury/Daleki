package model.map;

import model.EndGameException;
import model.moves.Mover;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    private List<MapObject> mapObjects = new ArrayList<>();

    private int width;
    private int height;

    private Mover mover;

    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.mover = new Mover(width, height);
        Doctor doctor = new Doctor(new Field(width/2 + 1, height/2 + 1));
        mapObjects.add(doctor);
//        mapObjects.add(new Dalek(doctor, new Field(5, 2))); //test dalek
//        mapObjects.add(new PileOfJunk(7, 7)); //test junk
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

    public List<MapObject> getMapObjects() {
        List<MapObject> notSoMovables = mover.getMapObjects();
        List<MapObject> result = new ArrayList<>();
        result.addAll(mapObjects);
        result.addAll(notSoMovables);
        return result;
    }

    public void move(String input){
        System.out.println(input);
        try {
            this.mover.moveAll(
                    mapObjects.stream()
                            .filter(MapObject::isMovable)
                            .map(it -> (Movable) it)
                            .collect(Collectors.toList()),
                    input);
        } catch (EndGameException e) {
            e.printStackTrace();   //-> you've lost!
        } catch (IllegalStateException e){  //->TODO sometimes may be teleporation, so there's need to make it out
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
