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
        this.view.bindTeleportTimesProperty(doctorTeleportationTimesProperty());
        this.view.bindUndosTimesPropetry(doctorUndoTimesProperty());
        this.view.bindSpareLivesProperty(doctorSpareLivesProperty());
        this.view.bindLevelProperty(worldLevelProperty());
        this.view.bindPowerUpsProperty(doctorPowerUpsProperty());

        this.paintWorld();
        this.setInput();
        this.inputer.setStageAndAddHandler(primaryStage);
    }

    private void paintWorld(){
        if (world.getGameOver()){
            this.view.setGameLostScene();
            return;
        }
        if (world.getTeleportationDialog()){
            world.resetTeleportationDialog();
            this.view.setTeleportationSideDialog();
            return;
        }

        if (world.getUndoDialog()){
            world.resetUndoDialog();
            this.view.setUndoSideDialog();
            return;
        }

        if (world.getDoctorDiesDialog()){
            world.resetDoctorDiesDialog();
            this.view.setDoctorDiesSideDialog();
            return;
        }
        if (world.getUpdateLevel()){
            world.resetUpdateLevel();
            this.view.setLevelUpSideDialog();
            return;
        }
        else if (world.getGameWon()){
            this.view.setGameWonScene();
            return;
        }
        this.view.setParameters(this.getWorldWidth(), this.getWorldHeight());
        this.view.paintWorld();

        ListIterator<MapObject> listIterator = world.getMapObjects().listIterator(world.getMapObjects().size());
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

    public int getWorldWidth(){
        return this.world.getWidth();
    }

    public int getWorldHeight(){
        return this.world.getHeight();
    }

    public IntegerProperty doctorTeleportationTimesProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.teleportationTimesProperty();
    }

    public IntegerProperty doctorUndoTimesProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.undoTimesProperty();
    }

    public IntegerProperty doctorSpareLivesProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.spareLivesProperty();
    }

    public IntegerProperty worldLevelProperty(){
        return this.world.getLevelManager().levelProperty();
    }

    public IntegerProperty doctorPowerUpsProperty(){
        Doctor doctor = world.getDoctor();
        return doctor.powerUpsProperty();
    }
}
