package presenter;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.map.World;
import view.InputOperationInterface;
import view.Inputer;
import view.View;

public class Presenter {
    private Stage stage;
    private World world;
    private View view;
    private Inputer inputer;

    public Presenter(Stage primaryStage, World world){
        this.stage = primaryStage;
        this.world = world;
        this.view = new View(primaryStage);
        this.paintWorld();
        this.setInput();
    }

    private void paintWorld(){

    }

    private void setInput(){
        this.inputer = new Inputer(this.stage);
        InputOperationInterface moveOnWorld = (input) -> this.world.move(input);
        this.inputer.subscribeToInput(moveOnWorld);
    }
}
