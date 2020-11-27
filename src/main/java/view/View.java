package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.map.World;

public class View {

    private Stage stage;

    private World world;

    public View(Stage primaryStage, World world){
        this.stage = primaryStage;
        this.world = world;
        initialize();
    }

    public void initialize(){
        stage.setTitle("Daleki");

        Group root = new Group();

        GridPane gridPane = new GridPane();

        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setStyle("-fx-background-color: grey;");

        for (int y = 0 ; y < world.getWidth() ; y++) {
            for (int x = 0 ; x < world.getHeight() ; x++) {
                Rectangle rect = new Rectangle(30, 30, Color.LIGHTGRAY);
                gridPane.add(rect, x, y);
            }
        }

        root.getChildren().add(gridPane);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
