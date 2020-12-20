package model.map;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
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

    public int width;
    public int height;

    private Doctor doctor;

    private final Mover mover;

    private Boolean gameOver = false;
    private Boolean gameWon = false;

    @Inject
    public World(Doctor doctor, @Named("worldWidth") int width, @Named("worldHeight") int height){
        this.doctor = doctor;
        this.width = width;
        this.height = height;
        System.out.println(this.width + this.height);
        this.mover = new Mover(width, height);
        this.generateExampleGame();
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
        if(gameOver || gameWon) {
            resetWorld();
        }
        else {
            move(input);
        }
    }

    private void move(String input){
        try {
            Direction directionInput = InputParser.parseInput(input);
            this.mapObjects = this.mover.moveAll(
                    mapObjects.stream()
                            .filter(item -> item instanceof Movable)
                            .map(Movable.class::cast)
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
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(5, 2)));
        mapObjects.add(new Dalek(doctor, new Field(7, 2)));
    }

    private void generateDoctorToMoveAround(){
        mapObjects.add(doctor);
    }

    private void generateDalekToMoveBehindTheDoctor(){
        mapObjects.add(doctor);
        mapObjects.add(new Dalek(doctor, new Field(1, 1)));
    }

    private void generateDalekToBoomIntoPieceOfJunk(){
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
        this.generateExampleGame();
        Doctor newDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        newDoctor.setTeleportationTimesProperty(teleportTimes);
        this.mover.clearMap();
        gameOver = false;
        gameWon = false;
    }

}
