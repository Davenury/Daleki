package view;

import com.sun.webkit.graphics.WCRectangle;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SidePanel {
    public VBox sidePanel;

    SidePanel(double sidePanelWidth, Label teleportTimes, Label spareLives){

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

        Rectangle space = new Rectangle(10, 30);
        space.setFill(Color.TRANSPARENT);

        teleportTimes.setTextFill(Color.LIGHTGRAY);
        Text teleports = new Text("Teleports left\t\t" + teleportTimes.getText());
        teleports.setTextAlignment(TextAlignment.LEFT);
        teleports.setFill(Color.LIGHTGRAY);

        spareLives.setTextFill(Color.LIGHTGRAY);
        Text lives = new Text("Lives left\t\t\t" + spareLives.getText());
        lives.setTextAlignment(TextAlignment.LEFT);
        lives.setFill(Color.LIGHTGRAY);

        teleports.setStyle("-fx-font-family: Langar; -fx-font-size: 20;");
        lives.setStyle("-fx-font-family: Langar; -fx-font-size: 20;");
        sidePanel.getStylesheets().add("https://fonts.googleapis.com/css2?family=Langar");

        sidePanel.getChildren().addAll(instruction, space, teleports, lives);
        this.sidePanel = sidePanel;
    }
}
