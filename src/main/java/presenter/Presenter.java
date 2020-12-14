package presenter;

import com.google.inject.Inject;
import javafx.beans.property.IntegerProperty;
import javafx.stage.Stage;
import model.creatures.Dalek;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.map.*;
import view.*;
import view.input.StringOperationInterface;
import view.input.InputerInterface;
import view.input.VoidOperationInterface;

import java.util.stream.Collectors;

public class Presenter {
    private World world;
    private View view;
    private final InputerInterface inputer;

    @Inject
    private Presenter(InputerInterface inputer){
        this.inputer = inputer;
    }

    public void setUpPresenter(Stage primaryStage, World world){
        this.world = world;
        this.view = new View(primaryStage);
        this.view.bindTeleportTimesProperty(doctorTeleportationTimesProperty());
        this.paintWorld();
        this.setInput();
        this.inputer.setStageAndAddHandler(primaryStage);
    }

    private void paintWorld(){
        if (world.getGameOver()){
            this.view.setGameLostScene();
            return;
        }
        else if (world.getGameWon()){
            this.view.setGameWonScene();
            return;
        }
        this.view.setParameters(this.getWorldWidth(), this.getWorldHeight());
        this.view.paintWorld();

        for(MapObject mapObject : world.getMapObjects()){
            Element element;
            if(mapObject instanceof Doctor)
                element = Element.DOCTOR;
            else if(mapObject instanceof Dalek)
                element = Element.DALEK;
            else
                element = Element.JUNK;
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
        Doctor doctor = (Doctor) world.getMapObjects().stream()
                .filter(mapObject -> mapObject instanceof Doctor)
                .collect(Collectors.toList()).get(0);
        return doctor.teleportationTimesProperty();
    }
}
