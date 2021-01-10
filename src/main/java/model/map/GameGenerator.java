package model.map;

import model.creatures.Dalek;
import model.creatures.Movable;
import model.things.NotSoMovable;
import model.things.PileOfJunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGenerator {

    private final World world;
    private final Random random = new Random();

    public GameGenerator(World world){
        this.world = world;
    }

    public void generateExampleGame(){
        this.generateRealGame();
    }

    private void generateRealGame(){
        world.addMapObject(world.getDoctor());
        int daleksNumber = (world.getLevel() + 2) * 2;
        System.out.println("Daleks number: " + daleksNumber);
        for(int i=0; i<daleksNumber; i++){
            generateRandomDalek();
        }
    }

    private void generateRandomDalek(){
        Field dalekField;
        do {
            int x = random.nextInt(11) + 1;
            int y = random.nextInt(11) + 1;
            dalekField = new Field(x, y);
        }while(daleksPlaceCondidtion(dalekField));
        world.addMapObject(new Dalek(world.getDoctor(), dalekField));
    }

    private boolean daleksPlaceCondidtion(Field dalekField){
        for(Movable movable : world.getMovables()){
            if(movable.getField().equals(dalekField)){
                return true;
            }
        }
        return false;
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

    private void generateDaleksToBoomAndOneSurvivor(){
        world.addMapObject(world.getDoctor());
        world.addMapObject(new Dalek(world.getDoctor(), new Field(5, 2)));
        world.addMapObject(new Dalek(world.getDoctor(), new Field(7, 2)));
        world.addMapObject(new Dalek(world.getDoctor(), new Field(1, 1)));
    }
}
