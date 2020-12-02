package model.map;

import com.google.inject.Inject;
import com.google.inject.Injector;
import diproviders.dimensions.IDimensionsSetter;
import model.EndGameException;
import model.creatures.Dalek;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.creatures.Movable;
import model.moves.Mover;
import model.other.ListConcatener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    private List<MapObject> mapObjects = new ArrayList<>();

    private int width;
    private int height;

    private Mover mover;
    private Injector injector;

    public Boolean gameOver = false;

    @Inject
    public World(IDimensionsSetter setter){
        this.width = setter.getWidth();
        this.height = setter.getHeight();
        this.mover = new Mover(width, height);
    }

    public void setInjector(Injector injector){
        this.injector = injector;
    }

    public void generateExampleGame(){
        this.generateDaleksToBoom();
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

    public List<MapObject> getMapObjects() {
        return ListConcatener.concatenate(mapObjects, mover.getMapObjects());
    }

    public void move(String input){
        System.out.println(input);
        try {
            this.mapObjects = this.mover.moveAll(
                    mapObjects.stream()
                            .filter(MapObject::isMovable)
                            .map(it -> (Movable) it)
                            .collect(Collectors.toList()),
                    input);
        } catch (EndGameException e) {
            e.printStackTrace();   //-> you've lost!
            this.gameOver = true;
        } catch (IllegalStateException e){  //->TODO sometimes may be teleporation, so there's need to make it out
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**To boom Daleks, please move one step up (press W key right after beginning of the game)*/
    private void generateDaleksToBoom(){
        Doctor doctor = injector.getInstance(Doctor.class);
        doctor.setField(new Field(6, 4));
        doctor.teleport();
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(5, 2))); //test dalek
        mapObjects.add(new Dalek(doctor, new Field(7, 2)));
    }

    private void generateDoctorToMoveAround(){
        Doctor doctor = injector.getInstance(Doctor.class);
        doctor.setField(new Field(width/2 + 1, height/2 + 1));
        mapObjects.add(doctor);
    }

    private void generateDalekToMoveBehindTheDoctor(){
        Doctor doctor = injector.getInstance(Doctor.class);
        doctor.setField(new Field(width/2 + 1, height/2 + 1));
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(1, 1)));
    }
}
