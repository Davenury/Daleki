package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static view.Element.DALEK;

public class View {

    private Stage stage;
    private Group root;

    private int worldHeight;
    private int worldWidth;

    private final int fieldSize = 60;
    private final int fieldGap = 2;

    private final String doctorImageSrc = "src/images/doctor.png";
    private final float doctorSize = 60;

    private final String dalekImageSrc = "src/images/dalek.png";
    private final float dalekSize = 60;

    private final float junkSize = fieldSize;
    private final Color junkColor = Color.DARKGRAY;

    public View(Stage primaryStage){
        this.stage = primaryStage;
    }

    public void setGameOverScene(){
        root = new Group();
        stage.setScene(new GameOver(root, worldHeight*fieldSize, worldWidth*fieldSize).getScene());
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
        Boolean pictureType = true;

        float elementSize = 0;
        String elementImageSrc = null;
        Color elementColor = null;

        switch(elementType) {
            case DOCTOR:
                elementImageSrc = doctorImageSrc;
                elementSize = doctorSize;
                break;
            case DALEK:
                elementImageSrc = dalekImageSrc;
                elementSize = dalekSize;
                break;
            default:
                pictureType = false;
        }
        switch(elementType) {
            case JUNK:
                elementSize = junkSize;
                elementColor = junkColor;
        }
            if (pictureType == false) {
                Shape element = new Circle(calculateElementPosition(gridX, false), calculateElementPosition(gridY, true), elementSize / 2,  elementColor);
                root.getChildren().add(element);
            }
            
            if (pictureType == true){
                Image image = null;
                try {
                    image = new Image(new FileInputStream(elementImageSrc));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ImageView imageView1 = new ImageView(image);
                imageView1.setY(calculateImageElementPosition(gridY, elementSize, true));
                imageView1.setX(calculateImageElementPosition(gridX, elementSize, false));

                imageView1.setFitHeight(elementSize);
                imageView1.setFitWidth(elementSize);
                root.getChildren().add(imageView1);
            }
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

    private double calculateImageElementPosition(int gridPosition, float picSize, boolean yAxis){
        if(yAxis) gridPosition = worldHeight - gridPosition + 1;
        return gridPosition*fieldSize - fieldSize + (gridPosition - 1)*fieldGap + (fieldSize - picSize)/2;
    }
}
