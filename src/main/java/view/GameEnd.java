package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.*;
import javafx.util.Duration;

import static java.lang.Math.random;


public class GameEnd {
    private Scene scene;
    private String message;

    public GameEnd(String endMessage, Group root, double height, double width) {
        this.scene = new Scene(root, width, height, Color.BLACK);
        this.message = endMessage;
        if (random()<0.5)
            this.matrixTheme(root);
        else this.colorCirclesTheme(root);
    }

    private String getDigits(int len){
        String s = "";
        for (int i = 0; i<len-1; i++){
            if (random()<0.5)
                s += "1";
            else s+= "0";
            s += "\n";
        }
        if (random()<0.5)
            s += "1";
        else s+= "0";
        return s;
    }
    public void matrixTheme(Group root){
        //Set Matrix Bg Green
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14, Color.web("#0bfc03")),
                        new Stop(0.28, Color.web("#90ff8c")),
                        new Stop(0.43, Color.web("#cdff8c")),
                        new Stop(0.57, Color.web("#90ff00")),
                        new Stop(0.71, Color.web("#16bf00")),
                        new Stop(0.85, Color.web("#89e37d")),
                        new Stop(1, Color.web("#c0ffb8"))));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        root.getChildren().add(colors);

        //Rectangles with 0/1
        Group rectangles = new Group();
        for (int i = 0; i < 30; i++) {
            Pane centeringPane = new StackPane();

            Rectangle rectangle = new Rectangle(10, 200, Color.web("white", 0.15));
            rectangle.setStrokeType(StrokeType.OUTSIDE);
            rectangle.setStroke(Color.web("white", 0.025));
            rectangle.setStrokeWidth(4);

            Text text = new Text();
            text.setText(this.getDigits(12));
            text.setStroke(Color.web("white", 0.85));
            text.setTextAlignment(TextAlignment.CENTER);

            centeringPane.getChildren().addAll(rectangle, text);
            rectangles.getChildren().add(centeringPane);
        }

        //add text
        //Creating a Text object
        Text text = new Text();

        //Setting the text to be added.
        text.setText(message);
        text.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 100));

        //Setting the color
        text.setFill(Color.web("white", 0.4));

        //Setting the Stroke
        text.setStrokeWidth(2);

        //Setting the stroke color

        text.setStroke(Color.web("white", 0.7));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setEffect(new BoxBlur(2, 2, 3));

        StackPane centeringPane = new StackPane();
        centeringPane.setPrefSize(scene.getWidth(), scene.getHeight());

        centeringPane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        //Blend rectangles with bg
        Group blendModeGroup =
                new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
                        Color.BLACK), rectangles, centeringPane), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);

        //add animation
        Timeline timeline = new Timeline();
        for (Node rectangle: rectangles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(rectangle.translateXProperty(), (random()) * scene.getWidth()),
                            new KeyValue(rectangle.translateYProperty(), (random()) * scene.getHeight())
                    ),
                    new KeyFrame(new Duration(40000), // set end position at 40s
                            new KeyValue(rectangle.translateXProperty(), (random()) * scene.getWidth()),
                            new KeyValue(rectangle.translateYProperty(), (random()) * scene.getHeight())
                    )
            );
        }

        // play 40s of animation
        timeline.play();
    }

    public void colorCirclesTheme(Group root){
        //StackPane is used to center elements
        Pane centeringPane = new StackPane();
        centeringPane.setPrefSize(scene.getWidth(), scene.getHeight());
        //Define the circles
        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        circles.setEffect(new BoxBlur(10, 10, 3));

        //Define the background gradient rectangle
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14, Color.web("#c0fe56")),
                        new Stop(0.28, Color.web("#5dfbc1")),
                        new Stop(0.43, Color.web("#64c2f8")),
                        new Stop(0.57, Color.web("#be4af7")),
                        new Stop(0.71, Color.web("#ed5fc2")),
                        new Stop(0.85, Color.web("#ef504c")),
                        new Stop(1, Color.web("#f2660f"))));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());

        //Combine circles and rectangle in blend mode
        Group blendModeGroup =
                new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
                        Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);

        //add text
        //Creating a Text object
        Text text = new Text();

        //Setting the text to be added.
        text.setText(message);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100));

        //setting the position of the text
        text.setX(scene.getWidth()/4);
        text.setY(scene.getHeight()/2);

        //Setting the color
        text.setFill(Color.web("white", 0.4));

        //Setting the Stroke
        text.setStrokeWidth(2);

        //Setting the stroke color
        text.setStroke(Color.web("white", 0.7));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setEffect(new BoxBlur(5, 5, 3));

        centeringPane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        root.getChildren().add(centeringPane);

        //add animation
        Timeline timeline = new Timeline();
        for (Node circle: circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(circle.translateXProperty(), (random()+0.1) * 800),
                            new KeyValue(circle.translateYProperty(), (random() + 0.1) * 600)
                    ),
                    new KeyFrame(new Duration(40000), // set end position at 40s
                            new KeyValue(circle.translateXProperty(), (random()+0.1) * 800),
                            new KeyValue(circle.translateYProperty(), (random()+0.1) * 600)
                    )
            );
        }

        // play 40s of animation
        timeline.play();
    }

    public Scene getScene() {
        return this.scene;
    }
}
