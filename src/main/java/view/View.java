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

public class View {

    private final Stage stage;
    private Group root;

    private int worldHeight;
    private int worldWidth;

    private double fieldSize;
    private final double fieldGap = 2.0d;

    private double doctorSize;
    private final String doctorTexturePath = "src/images/doctor.png";

    private double dalekSize;
    private final String dalekTexturePath = "src/images/dalek.png";

    private double junkSize;
    private final Color junkColor = Color.DARKGRAY;

    private final double sidePanelWidth = 200.0d;

    public View(Stage primaryStage){
        this.stage = primaryStage;
    }

    public void setGameLostScene(){
        setGameOverScene(false);
    }

    public void setGameWonScene(){
        setGameOverScene(true);
    }

    public void paintWorld(){
        root = new Group();
        stage.setTitle("Daleki");

        HBox gameView = new HBox();

        GridPane worldGrid = createWorldGrid();
        gameView.getChildren().add(worldGrid);

        VBox sidePanel = createSidePanel();
        gameView.getChildren().add(sidePanel);

        root.getChildren().add(gameView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void paintElement(Element elementType, int gridX, int gridY){
        double elementSize;
        Color elementColor = null;
        boolean pictureType = true;

        String elementTexturePath = null;

        switch (elementType) {
            case DOCTOR -> {
                elementTexturePath = doctorTexturePath;
                elementSize = doctorSize;
            }
            case DALEK -> {
                elementTexturePath = dalekTexturePath;
                elementSize = dalekSize;
            }
            default -> {
                elementSize = junkSize;
                elementColor = junkColor;
                pictureType = false;
            }
        }
        if (!pictureType) {
            Shape element = new Circle(calculateElementPosition(gridX, false),
                    calculateElementPosition(gridY, true), elementSize / 2.0d,  elementColor);
            root.getChildren().add(element);
        }

        if (pictureType){
            Image image = null;
            try {
                image = new Image(new FileInputStream(elementTexturePath));
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
        double gridMaxWidth = Screen.getPrimary().getBounds().getWidth() - sidePanelWidth - 100.0d;
        double gridMaxHeight = Screen.getPrimary().getBounds().getHeight() - 100.0d;

        double fieldMaxWidth = gridMaxWidth/(double)worldWidth - fieldGap;
        double fieldMaxHeight = gridMaxHeight/(double)worldHeight - fieldGap;

        fieldSize = Math.min(fieldMaxWidth, fieldMaxHeight);
        doctorSize = fieldSize * 0.9d;
        dalekSize = fieldSize * 0.8d;
        junkSize = fieldSize;

        stage.setResizable(false);
    }

    private double calculateElementPosition(int gridPosition, boolean yAxis){
        if(yAxis) gridPosition = worldHeight - gridPosition + 1;
        return (double)gridPosition*fieldSize - fieldSize/2.0d + ((double)gridPosition-1.0d)*fieldGap;
    }

    private double calculateImageElementPosition(int gridPosition, double picSize, boolean yAxis){
        if(yAxis) gridPosition = worldHeight - gridPosition + 1;
        return (double)gridPosition*fieldSize - fieldSize + ((double)gridPosition-1.0d)*fieldGap + (fieldSize-picSize)/2.0d;
    }

    private GridPane createWorldGrid(){
        GridPane worldGrid = new GridPane();

        worldGrid.setHgap(fieldGap);
        worldGrid.setVgap(fieldGap);
        worldGrid.setStyle("-fx-background-color: grey;");

        for(int x=0; x<this.worldWidth; x++) {
            for(int y=0; y<this.worldHeight; y++) {
                Rectangle rect = new Rectangle(fieldSize, fieldSize, Color.LIGHTGRAY);
                worldGrid.add(rect, x, y);
            }
        }
        return worldGrid;
    }

    private VBox createSidePanel(){
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
        return sidePanel;
    }

    private void setGameOverScene(boolean gameWon){
        root = new Group();
        String endMessage;
        if (gameWon)
            endMessage = "You've won!";
        else
            endMessage = "You've lost";
        stage.setScene(new GameEnd(endMessage, root, worldHeight*(fieldSize+fieldGap) - fieldGap,
                worldWidth*(fieldSize+fieldGap) - fieldGap + sidePanelWidth).getScene());
    }
}
