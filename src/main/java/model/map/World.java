package model.map;

import com.google.inject.Inject;
import com.google.inject.Injector;
import diproviders.dimensions.IDimensionsSetter;
import exceptions.EndGameException;
import exceptions.GameWonException;
import exceptions.TeleportationTimesException;
import javafx.beans.property.IntegerProperty;
import model.creatures.*;
import model.moves.Mover;
import model.other.ListConcatener;
import model.things.NotSoMovable;
import model.things.PileOfJunk;
import view.input.InputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    private List<MapObject> mapObjects = new ArrayList<>();

    private final int width;
    private final int height;

    private final Mover mover;
    private Injector injector;

    private Boolean gameOver = false;
    private Boolean gameWon = false;

    @Inject
    private World(IDimensionsSetter setter){
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

    public boolean getGameOver(){ return this.gameOver; }

    public boolean getGameWon() { return this.gameWon; }

    public List<MapObject> getMapObjects() {
        return ListConcatener.concatenate(mapObjects, mover.getMapObjects());
    }

    public void update(String input){
        System.out.println(input);
        if(gameOver || gameWon)
            resetWorld();
        else
            move(input);
    }

    private void move(String input){
        try {
            Direction directionInput = InputParser.parseInput(input);
            this.mapObjects = this.mover.moveAll(
                    mapObjects.stream()
                            .filter(MapObject::isMovable)
                            .map(it -> (Movable) it)
                            .collect(Collectors.toList()),
                    directionInput);
        } catch (EndGameException e) {
            e.printStackTrace();
            this.gameOver = true;
        } catch (GameWonException e) {
            e.printStackTrace();
            this.gameWon = true;
        }
        catch (IllegalStateException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (TeleportationTimesException e){
            //TODO -> show that you have no teleportations - dialog maybe?
        }
    }

    /**To boom Daleks, please move one step up (press W key right after beginning of the game)*/
    private void generateDaleksToBoom(){
        Doctor doctor = DoctorFactory.createDoctor(new Field(6, 4), injector);
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(5, 2)));
        mapObjects.add(new Dalek(doctor, new Field(7, 2)));
    }

    private void generateDoctorToMoveAround(){
        Doctor doctor = DoctorFactory.createDoctor(new Field(width/2 + 1, height/2 + 1), injector);
        mapObjects.add(doctor);
    }

    private void generateDalekToMoveBehindTheDoctor(){
        Doctor doctor = DoctorFactory.createDoctor(new Field(width/2 + 1, height/2 + 1), injector);
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(1, 1)));
    }

    private void generateDalekToBoomIntoPieceOfJunk(){
        Doctor doctor = DoctorFactory.createDoctor(new Field(6, 4), injector);
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(4, 4)));
        List<NotSoMovable> initialMap = new ArrayList<>();
        initialMap.add(new PileOfJunk(5,4));
        this.mover.setInitialMap(initialMap);
    }

    private void resetWorld(){
        Doctor oldDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        oldDoctor.resetTeleportationTimes();
        IntegerProperty teleportTimes = oldDoctor.teleportationTimesProperty();
        mapObjects.clear();
        WorldFactory.resetWorld();
        Doctor newDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        newDoctor.setTeleportationTimesProperty(teleportTimes);
        gameOver = false;
        gameWon = false;
    }

}
