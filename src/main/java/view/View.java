package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class View {

    private Stage stage;
    private Group root;

    private int worldHeight;
    private int worldWidth;

    private final int fieldSize = 60;
    private final int fieldGap = 2;

    private Circle doctor;
    private final float doctorSize = 50;

    public View(Stage primaryStage){
        this.stage = primaryStage;
    }

    public void paintWorld(){
        root = new Group();
        stage.setTitle("Daleki");

        GridPane gridPane = new GridPane();

        gridPane.setHgap(fieldGap);
        gridPane.setVgap(fieldGap);
        gridPane.setStyle("-fx-background-color: grey;");

        for(int x=0; x<this.worldWidth; x++) {
            for(int y=0; y<this.worldHeight; y++) {
                Rectangle rect = new Rectangle(fieldSize, fieldSize, Color.LIGHTGRAY);
                gridPane.add(rect, x, y);
            }
        }

        root.getChildren().add(gridPane);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void paintDoctor(int gridX, int gridY){
        doctor = new Circle(calculateElementPosition(gridX), calculateElementPosition(gridY), doctorSize/2, Color.BLUE);
        root.getChildren().add(doctor);
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

    private int calculateElementPosition(int gridPosition){
        return gridPosition*fieldSize - fieldSize/2 + (gridPosition-1)*fieldGap;
    }
}
