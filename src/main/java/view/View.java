package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class View {

    private Stage stage;

    public View(Stage primaryStage){
        this.stage = primaryStage;
        initialize();
    }

    public void initialize(){
        stage.setTitle("Daleki");
        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene(theScene);

        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        stage.show();
    }
}
