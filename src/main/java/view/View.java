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

    private int worldHeight;
    private int worldWidth;

    public View(Stage primaryStage){
        this.stage = primaryStage;
    }

    public void paintWorld(){
        stage.setTitle("Daleki");

        Group root = new Group();

        GridPane gridPane = new GridPane();

        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setStyle("-fx-background-color: grey;");

        for (int y = 0 ; y < this.worldWidth; y++) {
            for (int x = 0 ; x < this.worldHeight; x++) {
                Rectangle rect = new Rectangle(30, 30, Color.LIGHTGRAY);
                gridPane.add(rect, x, y);
            }
        }

        root.getChildren().add(gridPane);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setParameters(int worldWidth, int worldHeight){
        this.setWorldWidth(worldWidth);
        this.setWorldHeight(worldHeight);
    }

    private void setWorldWidth(int worldWidth){
        this.worldWidth = worldWidth;
    }

    private void setWorldHeight(int worldHeight){
        this.worldHeight = worldHeight;
    }
}
