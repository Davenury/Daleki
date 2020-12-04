package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static view.Element.DALEK;

public class View {

    private Stage stage;
    private Group root;

    private int worldHeight;
    private int worldWidth;

    private float fieldSize;
    private final int fieldGap = 2;

    private float doctorSize = 60;
    private final Color doctorColor = Color.BLUE;

    private float dalekSize = 60;
    private final Color dalekColor = Color.RED;
    private final String doctorImageSrc = "src/images/doctor.png";

    private final String dalekImageSrc = "src/images/dalek.png";

    private float junkSize;
    private final Color junkColor = Color.DARKGRAY;

    private final float sidePanelWidth = 200;

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

        HBox hBox = new HBox();

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

        hBox.getChildren().add(gridPane);

        VBox sidePanel = new VBox();
        sidePanel.setPrefWidth(sidePanelWidth);
        sidePanel.setAlignment(Pos.CENTER);
        sidePanel.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        Text instruction = new Text(
                                "W/8\t\tgo North\n" +
                                "9\t\tgo North-East\n" +
                                "D/6\t\tgo East\n" +
                                "3\t\tgo South-East\n" +
                                "S/2\t\tgo South\n" +
                                "1\t\tgo South-West\n" +
                                "A/4\t\tgo West\n" +
                                "7\t\tgo North-West\n" +
                                "Q/5\t\tstay\n" +
                                "T\t\tteleport"
        );
        instruction.setFill(Color.LIGHTGRAY);
        sidePanel.getChildren().add(instruction);

        hBox.getChildren().add(sidePanel);

        root.getChildren().add(hBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void paintElement(Element elementType, int gridX, int gridY){
        float elementSize = 0;
        Color elementColor = null;
        Boolean pictureType = true;

        String elementImageSrc = null;

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
        this.setWindowSize();
    }

    private void setWorldWidth(int worldWidth){
        this.worldWidth = worldWidth;
    }

    private void setWorldHeight(int worldHeight){
        this.worldHeight = worldHeight;
    }

    private void setWindowSize(){
        double gridMaxWidth = Screen.getPrimary().getBounds().getWidth() - sidePanelWidth - 100;
        double gridMaxHeight = Screen.getPrimary().getBounds().getHeight() - 100;

        double fieldMaxWidth = gridMaxWidth/worldWidth - fieldGap;
        double fieldMaxHeight = gridMaxHeight/worldHeight - fieldGap;

        fieldSize = (float) (fieldMaxWidth < fieldMaxHeight ? fieldMaxWidth : fieldMaxHeight);
        doctorSize = (float) (fieldSize * 0.9);
        dalekSize = (float) (fieldSize * 0.8);
        junkSize = fieldSize;

        stage.setResizable(false);
    }

    private double calculateElementPosition(int gridPosition, boolean yAxis){
        if(yAxis) gridPosition = worldHeight - gridPosition + 1;
        return gridPosition*fieldSize - fieldSize/2 + (gridPosition-1)*fieldGap;
    }

    private double calculateImageElementPosition(int gridPosition, float picSize, boolean yAxis){
        if(yAxis) gridPosition = worldHeight - gridPosition + 1;
        return gridPosition*fieldSize - fieldSize + (gridPosition - 1)*fieldGap + (fieldSize - picSize)/2;
    }
}
