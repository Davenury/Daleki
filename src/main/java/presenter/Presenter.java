package presenter;

import com.google.inject.Inject;
import javafx.stage.Stage;
import model.creatures.Dalek;
import model.creatures.Doctor;
import model.creatures.MapObject;
import model.map.*;
import view.*;
import view.input.StringOperationInterface;
import view.input.InputerInterface;
import view.input.VoidOperationInterface;

public class Presenter {
    private Stage stage;
    private World world;
    private View view;
    private InputerInterface inputer;

    @Inject
    private Presenter(InputerInterface inputer){
        this.inputer = inputer;
    }

    public void setUpPresenter(Stage primaryStage, World world){
        this.stage = primaryStage;
        this.world = world;
        this.view = new View(primaryStage);
        this.paintWorld();
        this.setInput();
        this.inputer.setStageAndAddHandler(primaryStage);
    }

    private void paintWorld(){
        if (this.world.gameOver){
            this.view.setGameOverScene();
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
}
