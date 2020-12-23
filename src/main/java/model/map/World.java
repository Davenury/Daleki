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
import view.input.InputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    private List<MapObject> mapObjects = new ArrayList<>();

    public int width;
    public int height;

    private final Doctor doctor;

    private final Mover mover;

    private Boolean gameOver = false;
    private Boolean gameWon = false;

    private GameGenerator gameGenerator;

    @Inject
    public World(Doctor doctor, @Named("worldWidth") int width, @Named("worldHeight") int height){
        this.doctor = doctor;
        this.width = width;
        this.height = height;
        this.gameGenerator = new GameGenerator(this);
        this.gameGenerator.generateExampleGame();
        this.mover = new Mover(width, height, mapObjects);
    }


    public int getWidth(){ return width; }

    public int getHeight(){ return height; }

    public boolean getGameOver(){ return this.gameOver; }

    public boolean getGameWon() { return this.gameWon; }

    public List<MapObject> getMapObjects() {
        return ListConcatener.concatenate(mapObjects, mover.getMapObjects());
    }

    public Doctor getDoctor() { return this.doctor; }

    public Mover getMover() { return this.mover; }

    public void update(String input){
        System.out.println(input);
        if(gameOver || gameWon) {
            System.out.println("here");
            resetWorld();
        }
        else {
            move(input);
        }
    }

    public void addMapObject(MapObject mapObject){
        this.mapObjects.add(mapObject);
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
            this.gameOver = true;
        } catch (GameWonException e) {
            this.gameWon = true;
        }
        catch (IllegalStateException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            //TODO -> I would also do it as a dialog?
        } catch (TeleportationTimesException e){
            //TODO -> show that you have no teleportations - dialog maybe?
        }
    }

    private void resetWorld(){
        Doctor oldDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        oldDoctor.resetTeleportationTimes();
        oldDoctor.resetSpareLives();
        IntegerProperty teleportTimes = oldDoctor.teleportationTimesProperty();
        IntegerProperty spareLives = oldDoctor.spareLivesProperty();
        //IntegerProperty teleportTimes = oldDoctor.teleportationTimesProperty();
        mapObjects = new ArrayList<>();
        this.gameGenerator.generateExampleGame();
        Doctor newDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        newDoctor.setTeleportationTimesProperty(teleportTimes);
        newDoctor.setSpareLivesProperty(spareLives);
        newDoctor.setTeleportationTimesProperty(teleportTimes);
        this.mover.clearMap();
        gameOver = false;
        gameWon = false;
    }

}
