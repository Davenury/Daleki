package presenter;

import javafx.stage.Stage;
import model.map.*;
import view.*;

public class Presenter {
    private final Stage stage;
    private final World world;
    private final View view;

    public Presenter(Stage primaryStage, World world){
        this.stage = primaryStage;
        this.world = world;
        this.view = new View(primaryStage);
        this.paintWorld();
        this.setInput();
    }

    private void paintWorld(){
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
        Inputer inputer = new Inputer(this.stage);
        InputOperationInterface moveOnWorld = this.world::move;
        RepaintWorldOperationInterface repaint = this::paintWorld;
        inputer.subscribeToInput(moveOnWorld, repaint);
    }

    public int getWorldWidth(){
        return this.world.getWidth();
    }

    public int getWorldHeight(){
        return this.world.getHeight();
    }
}
