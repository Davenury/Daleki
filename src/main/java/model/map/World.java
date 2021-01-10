package model.map;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import exceptions.*;
import javafx.beans.property.IntegerProperty;
import model.creatures.*;
import model.moves.Mover;
import model.other.LevelManager;
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

    private Mover mover;

    private final LevelManager levelManager = new LevelManager();

    private Boolean gameOver = false;
    private Boolean gameWon = false;
    private Boolean updateLevelDialog = false;
    private Boolean teleportationDialog = false;
    private Boolean undoDialog = false;
    private Boolean doctorDiesDialog = false;
    private Boolean noPowerUpsDialog = false;

    private final GameGenerator gameGenerator;

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

    public LevelManager getLevelManager(){ return this.levelManager;}

    public boolean getGameOver(){ return this.gameOver; }

    public boolean getGameWon() { return this.gameWon; }

    public boolean getUpdateLevel(){ return this.updateLevelDialog; }

    public void resetUpdateLevel(){
        this.updateLevelDialog = false;
    }

    public boolean getTeleportationDialog(){ return this.teleportationDialog; }

    public boolean getUndoDialog(){return this.undoDialog;}

    public void resetUndoDialog(){this.undoDialog = false;}

    public void resetTeleportationDialog(){ this.teleportationDialog = false; }

    public boolean getDoctorDiesDialog(){ return this.doctorDiesDialog; }

    public void resetDoctorDiesDialog(){
        this.doctorDiesDialog = false;
    }

    public boolean getNoPowerUpsDialog() { return this.noPowerUpsDialog; }

    public void resetNoPowerUpsDialog() {
        this.noPowerUpsDialog = false;
    }

    public List<MapObject> getMapObjects() {
        return ListConcatener.concatenate(mapObjects, mover.getMapObjects());
    }

    public Doctor getDoctor() { return this.doctor; }

    public Mover getMover() { return this.mover; }

    public void update(String input){
        System.out.println(input);
        if(gameOver || gameWon) {
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
        }
        catch (EndGameException e) {
            this.gameOver = true;
            this.levelManager.resetLevel();
        } catch (NextLevelException e) {
            this.updateLevelDialog = true;
            try {
                this.levelManager.incrementLevel();
            } catch (GameWonException gameWonException) {
                this.gameWon = true;
            }
            resetWorld();
        } catch (IllegalStateException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        catch (TeleportationTimesException e){
            teleportationDialog = true;
        } catch (PowerUpException e) {
            e.printStackTrace();
        } catch(CantUndoException e){
            undoDialog = true;
        }
        if (this.doctor.getDiedInPrevRound()){
            this.doctorDiesDialog = true;
        }
    }

    private void resetWorld(){
        Doctor oldDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        oldDoctor.resetTeleportationTimes();
        oldDoctor.resetSpareLives();
        oldDoctor.resetPowerUps();
        IntegerProperty teleportTimes = oldDoctor.teleportationTimesProperty();
        IntegerProperty spareLives = oldDoctor.spareLivesProperty();
        //IntegerProperty level = this.levelManager.levelProperty();

        mapObjects = new ArrayList<>();
        this.gameGenerator.generateExampleGame();
        Doctor newDoctor = (Doctor) mapObjects.stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        newDoctor.setTeleportationTimesProperty(teleportTimes);
        newDoctor.setSpareLivesProperty(spareLives);

        this.mover = new Mover(width, height, mapObjects);
        gameOver = false;
        gameWon = false;
    }

    public Integer getLevel(){
        return this.levelManager.getCurrentLevel();
    }

    public List<Movable> getMovables(){
        return this.mapObjects.stream()
                .filter(object -> object instanceof Movable)
                .map(object -> (Movable) object)
                .collect(Collectors.toList());
    }

}
