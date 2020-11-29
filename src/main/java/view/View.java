package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class View {

    private Stage stage;
    private Group root;

    private int worldHeight;
    private int worldWidth;

    private final int fieldSize = 60;
    private final int fieldGap = 2;

    private final float doctorSize = 50;
    private final Color doctorColor = Color.BLUE;

    private final float dalekSize = 40;
    private final Color dalekColor = Color.RED;

    private final float junkSize = fieldSize;
    private final Color junkColor = Color.DARKGRAY;

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

    public void paintElement(Element elementType, int gridX, int gridY){
        float elementSize;
        Color elementColor;
        switch(elementType) {
            case DOCTOR:
                elementSize = doctorSize;
                elementColor = doctorColor;
                break;
            case DALEK:
                elementSize = dalekSize;
                elementColor = dalekColor;
                break;
            default:
                elementSize = junkSize;
                elementColor = junkColor;
        }
        Shape element = new Circle(calculateElementPosition(gridX, false), calculateElementPosition(gridY, true), elementSize/2, elementColor);
        root.getChildren().add(element);

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

    private double calculateElementPosition(int gridPosition, boolean yAxis){
        if(yAxis) gridPosition = worldHeight - gridPosition + 1;
        return gridPosition*fieldSize - (double) fieldSize/2 + (gridPosition-1)*fieldGap;
    }
}
