package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SidePanel extends VBox{

    private final double width;

    private final Label teleportTimes;
    private final Label spareLives;
    private final Label level;
    private final Label powerUps;

    private GridPane dialogBox;
    private final String dialogBoxTexturePath = "src/images/phone_booth.png";

    public SidePanel(double sidePanelWidth, Label teleportTimes, Label spareLives, Label level, Label powerUps){
        this.width = sidePanelWidth;

        configureSidePanel();

        this.teleportTimes = teleportTimes;
        this.spareLives = spareLives;
        this.level = level;
        this.powerUps = powerUps;

        GridPane currentGameData = createCurrentGameData();

        createDialogBox();
        dialogBoxSetNeutral();

        this.getChildren().addAll(this.dialogBox, currentGameData);
    }

    private void createDialogBox(){
        this.dialogBox = new GridPane();

        this.dialogBox.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        
        //centering the column
        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.CENTER);
        dialogBox.getColumnConstraints().add(col);
        this.dialogBox.setAlignment(Pos.CENTER);
        this.dialogBox.setPadding(new Insets(10, 10, 10, 10));
        
        //Setting the vertical and horizontal gaps between the columns
        this.dialogBox.setVgap(5);
        this.dialogBox.setHgap(15);
        
        //Setting size for the pane
        dialogBox.setMinSize(225, 350);
    }

    public void dialogBoxSetNeutral(){
        this.dialogBox.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        this.dialogBox.getChildren().clear();
        Text instruction = createInstructions();
        this.dialogBox.add(instruction, 0, 0);
    }


    public void dialogBoxSetMessageTeleportationExceeded(){
        setDialogBoxTextAndPicture("You've run out of\nteleportations!", dialogBoxTexturePath, Color.BURLYWOOD);
    }

    public void dialogBoxSetMessageLevelUp(){
        setDialogBoxTextAndPicture("LEVEL UP!", dialogBoxTexturePath, Color.CORNFLOWERBLUE);
    }

    public void dialogBoxSetMessageLostLife(){
        setDialogBoxTextAndPicture("You lost life!", dialogBoxTexturePath, Color.ROSYBROWN);
    }

    public void setDialogBoxSetMessageNoPowerUps(){
        setDialogBoxTextAndPicture("You have no\nPower Ups!", dialogBoxTexturePath, Color.CRIMSON);
    }

    private void setDialogBoxTextAndPicture(String text, String filePath, Color color){
        this.dialogBox.getChildren().clear();

        this.dialogBox.setBackground(new Background(new BackgroundFill(color,
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        Text message = new Text(text);
        message.setStyle("-fx-font-family: Audiowide; -fx-font-size: 20;");

        Image image = null;
        try {
            image = new Image(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);

        //imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150);

        this.dialogBox.add(message, 0, 0);
        this.dialogBox.add(imageView, 0, 1);
    }

    private void configureSidePanel(){
        this.setPrefWidth(width);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        this.getStylesheets().add("https://fonts.googleapis.com/css2?family=Langar");
        this.getStylesheets().add("https://fonts.googleapis.com/css2?family=Audiowide&display=swap");
    }

    private Text createInstructions(){
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
                        "T\t\tteleport\n" +
                        "U\t\tundo\n"
        );
        instruction.setFill(Color.LIGHTGRAY);

        instruction.setStyle("-fx-font-size: 15;");
        return instruction;
    }

    private GridPane createCurrentGameData(){
        GridPane currentGameData = new GridPane();

        currentGameData.setPrefWidth(this.getPrefWidth());

        //Setting the padding
        currentGameData.setPadding(new Insets(10, 10, 10, 10));
        //Setting the vertical and horizontal gaps between the columns
        currentGameData.setVgap(5);
        currentGameData.setHgap(15);
        //Setting the Grid alignment
        currentGameData.setAlignment(Pos.CENTER_LEFT);

        currentGameData.add(createFormatedText("Teleports left"), 0, 0);
        currentGameData.add(createFormatedText(teleportTimes.getText()), 1, 0);
        currentGameData.add(createFormatedText("Lives left"), 0, 1);
        currentGameData.add(createFormatedText(spareLives.getText()), 1, 1);
        currentGameData.add(createFormatedText("LEVEL"), 0, 2);
        currentGameData.add(createFormatedText(level.getText()), 1, 2);
        currentGameData.add(createFormatedText("Power Ups"), 0, 3);
        currentGameData.add(createFormatedText(powerUps.getText()), 1, 3);

        return currentGameData;
    }

    private Text createFormatedText(String text0){
        Text text = new Text(text0);
        text.setFill(Color.LIGHTGRAY);
        text.setStyle("-fx-font-family: Audiowide; -fx-font-size: 20;");
        return text;
    }
}
