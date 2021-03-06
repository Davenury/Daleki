package view;

import javafx.beans.property.IntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

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
    private final String junkTexturePath = "src/images/scrap.png";

    private double powerUpSize;
    private final String powerUpTexturePath = "src/images/powerup.png";

    private final double sidePanelWidth = 200.0d;
    private SidePanel sidePanel;

    private final Label teleportTimes = new Label();
    private final Label undosTimes = new Label();
    private final Label spareLives = new Label();
    private final Label level = new Label();
    private final Label powerUps = new Label();

    public View(Stage primaryStage){
        this.stage = primaryStage;
    }

    public void setParameters(int worldWidth, int worldHeight){
        this.setWorldWidth(worldWidth);
        this.setWorldHeight(worldHeight);
        this.setWindowSize();
    }

    public void paintWorld(){
        root = new Group();
        stage.setTitle("Daleki");

        HBox gameView = new HBox();

        GridPane worldGrid = createWorldGrid();
        gameView.getChildren().add(worldGrid);

        this.sidePanel = new SidePanel(sidePanelWidth, teleportTimes, spareLives, level, undosTimes);
        gameView.getChildren().add(sidePanel);

        root.getChildren().add(gameView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void paintElement(Element elementType, int gridX, int gridY){
        switch (elementType) {
            case DOCTOR -> paintImageTypeElement(doctorTexturePath, doctorSize, gridX, gridY);
            case DALEK -> paintImageTypeElement(dalekTexturePath, dalekSize, gridX, gridY);
            case JUNK -> paintImageTypeElement(junkTexturePath, junkSize, gridX, gridY);
            case POWERUP -> paintImageTypeElement(powerUpTexturePath, powerUpSize, gridX, gridY);
        }
    }


    public void setGameLostScene(){
        setGameOverScene(false);
    }

    public void setGameWonScene(){ setGameOverScene(true); }


    public void setTeleportationSideDialog() {
        this.sidePanel.setDialogBoxMessage("You've run out of\nteleportations!", Color.BURLYWOOD);
    }

    public void setUndoSideDialog() {
        this.sidePanel.setDialogBoxMessage("You've run out of\nundos!", Color.BURLYWOOD);
    }

    public void setLevelUpSideDialog() {
        this.sidePanel.setDialogBoxMessage("LEVEL UP!", Color.CORNFLOWERBLUE);
    }

    public void setDoctorLostLifeDialog() {
        this.sidePanel.setDialogBoxMessage("You lost life!", Color.ROSYBROWN);
    }


    public void bindTeleportTimesProperty(IntegerProperty teleportTimes){
        this.teleportTimes.textProperty().bindBidirectional(teleportTimes, new NumberStringConverter());
    }

    public void bindUndosTimesPropetry(IntegerProperty undosTimes){
        this.undosTimes.textProperty().bindBidirectional(undosTimes, new NumberStringConverter());
    }

    public void bindSpareLivesProperty(IntegerProperty spareLives) {
        this.spareLives.textProperty().bindBidirectional(spareLives, new NumberStringConverter());

    }
    public void bindLevelProperty(IntegerProperty level){
        this.level.textProperty().bindBidirectional(level, new NumberStringConverter());
    }

    public void bindPowerUpsProperty(IntegerProperty powerUps){
        this.powerUps.textProperty().bindBidirectional(powerUps, new NumberStringConverter());
    }

    private void paintImageTypeElement(String elementTexturePath, double elementSize, int gridX, int gridY){
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
        junkSize = fieldSize * 0.9d;
        powerUpSize = fieldSize * 0.9d;

        stage.setResizable(false);
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
