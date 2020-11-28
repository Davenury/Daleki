package presenter;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.map.World;
import view.InputOperationInterface;
import view.Inputer;
import view.View;

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
    }

    private void setInput(){
        Inputer inputer = new Inputer(this.stage);
        InputOperationInterface moveOnWorld = this.world::move;
        inputer.subscribeToInput(moveOnWorld);
    }

    public int getWorldWidth(){
        return this.world.getWidth();
    }

    public int getWorldHeight(){
        return this.world.getHeight();
    }
}
