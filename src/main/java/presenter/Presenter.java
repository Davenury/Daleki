package presenter;

import javafx.stage.Stage;
import model.map.World;
import view.View;

public class Presenter {
    private Stage stage;
    private World world;
    private View view;

    public Presenter(Stage primaryStage, World world){
        this.stage = primaryStage;
        this.world = world;
        this.view = new View(primaryStage);
        paintWorld();
    }

    private void paintWorld(){

    }
}
