package model.map;

import model.creatures.Dalek;
import model.things.NotSoMovable;
import model.things.PileOfJunk;

import java.util.ArrayList;
import java.util.List;

public class GameGenerator {

    private final World world;

    public GameGenerator(World world){
        this.world = world;
    }

    public void generateExampleGame(){
        this.generateDaleksToBoom();
    }

    /**To boom Daleks, please move one step up (press W key right after beginning of the game)*/
    private void generateDaleksToBoom(){
        world.addMapObject(world.getDoctor());
        world.addMapObject(new Dalek(world.getDoctor(), new Field(5, 2)));
        world.addMapObject(new Dalek(world.getDoctor(), new Field(7, 2)));
    }

    private void generateDoctorToMoveAround(){
        world.addMapObject(world.getDoctor());
    }

    private void generateDalekToMoveBehindTheDoctor(){
        world.addMapObject(world.getDoctor());
        world.addMapObject(new Dalek(world.getDoctor(), new Field(1, 1)));
    }

    private void generateDalekToBoomIntoPieceOfJunk(){
        world.addMapObject(world.getDoctor());
        world.addMapObject(new Dalek(world.getDoctor(), new Field(4, 4)));
        List<NotSoMovable> initialMap = new ArrayList<>();
        initialMap.add(new PileOfJunk(5,4));
        world.getMover().setInitialMap(initialMap);
    }
}
