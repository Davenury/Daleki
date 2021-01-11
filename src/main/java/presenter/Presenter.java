package presenter;

import com.google.inject.Inject;
import javafx.beans.property.IntegerProperty;
import javafx.stage.Stage;
import model.creatures.Dalek;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.map.*;
import model.things.PileOfJunk;
import view.*;
import view.input.StringOperationInterface;
import view.input.InputerInterface;
import view.input.VoidOperationInterface;

import java.util.*;
import java.util.ListIterator;

public class Presenter {
    private World world;
    private View view;
    private final InputerInterface inputer;

    @Inject
    public Presenter(InputerInterface inputer){
        this.inputer = inputer;
    }

    public void setUpPresenter(Stage primaryStage, World world) {
        this.world = world;
        this.view = new View(primaryStage);
        this.bindPropertiesToView();
        this.paintWorld();
        this.setInput();
        this.inputer.setStageAndAddHandler(primaryStage);
    }

    private void paintWorld(){
        if(showDialogOrEndGame()){
            return;
        }
        this.view.setParameters(this.getWorldWidth(), this.getWorldHeight());
        this.view.paintWorld();

        List<MapObject> mapObjectList = world.getMapObjects();
        ListIterator<MapObject> listIterator = mapObjectList.listIterator(mapObjectList.size());
        while(listIterator.hasPrevious()){
            MapObject mapObject = listIterator.previous();
            Element element;
            if(mapObject instanceof Doctor)
                element = Element.DOCTOR;
            else if(mapObject instanceof Dalek)
                element = Element.DALEK;
            else if(mapObject instanceof PileOfJunk)
                element = Element.JUNK;
            else
                element = Element.POWERUP;
            this.view.paintElement(element, mapObject.getField().getX(), mapObject.getField().getY());
        }
    }

    private void setInput(){
        StringOperationInterface updateWorld = this.world::update;
        VoidOperationInterface repaint = this::paintWorld;
        this.inputer.subscribeToInput(updateWorld, repaint);
    }

    private void bindPropertiesToView(){
        this.view.bindTeleportTimesProperty(doctorTeleportationTimesProperty());
        this.view.bindUndosTimesPropetry(doctorUndoTimesProperty());
        this.view.bindSpareLivesProperty(doctorSpareLivesProperty());
        this.view.bindLevelProperty(worldLevelProperty());
        this.view.bindPowerUpsProperty(doctorPowerUpsProperty());
    }

    private int getWorldWidth(){
        return this.world.getWidth();
    }

    private int getWorldHeight(){
        return this.world.getHeight();
    }

    private IntegerProperty doctorTeleportationTimesProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.teleportationTimesProperty();
    }

    private IntegerProperty doctorUndoTimesProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.undoTimesProperty();
    }

    private IntegerProperty doctorSpareLivesProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.spareLivesProperty();
    }

    private IntegerProperty worldLevelProperty(){
        return this.world.getLevelManager().levelProperty();
    }

    private IntegerProperty doctorPowerUpsProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.powerUpsProperty();
    }

    private boolean showDialogOrEndGame() {
        if (world.getGameOver()) {
            this.view.setGameLostScene();
        } else if (world.getGameWon()) {
            this.view.setGameWonScene();
        } else if (world.getTeleportationDialog()) {
            world.resetTeleportationDialog();
            this.view.setTeleportationSideDialog();
        } else if (world.getUndoDialog()) {
            world.resetUndoDialog();
            this.view.setUndoSideDialog();
        } else if (world.getDoctorDiesDialog()) {
            world.resetDoctorDiesDialog();
            this.view.setDoctorLostLifeDialog();
        } else if (world.getUpdateLevel()) {
            world.resetUpdateLevel();
            this.view.setLevelUpSideDialog();
        } else {
            return false;
        }
        return true;
    }
}
