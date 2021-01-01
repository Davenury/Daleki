package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SidePanel extends VBox{

    private final double width;

    private final Label teleportTimes;
    private final Label spareLives;

    public SidePanel(double sidePanelWidth, Label teleportTimes, Label spareLives){
        this.width = sidePanelWidth;

        configureSidePanel();

        this.teleportTimes = teleportTimes;
        this.spareLives = spareLives;

        Text instruction = createInstructions();
        VBox currentGameData = createCurrentGameData();

        this.getChildren().addAll(instruction, currentGameData);
    }

    private void configureSidePanel(){
        this.setPrefWidth(width);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        this.getStylesheets().add("https://fonts.googleapis.com/css2?family=Langar");
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
                        "T\t\tteleport"
        );
        instruction.setFill(Color.LIGHTGRAY);
        return instruction;
    }

    private VBox createCurrentGameData(){
        VBox currentGameData = new VBox();

        currentGameData.setPrefWidth(this.getPrefWidth());
        currentGameData.setAlignment(Pos.CENTER);
        currentGameData.setSpacing(10);

        Text teleports = createTextFromLabel(teleportTimes, "Teleports left");
        Text lives = createTextFromLabel(spareLives, "Lives left");

        currentGameData.getChildren().addAll(teleports, lives);
        return currentGameData;
    }

    private Text createTextFromLabel(Label label, String name){
        Text text = new Text(name + "\t\t" + label.getText());
        text.setFill(Color.LIGHTGRAY);
        text.setStyle("-fx-font-family: Langar; -fx-font-size: 20;");
        return text;
    }
}
