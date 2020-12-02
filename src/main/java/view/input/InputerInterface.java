package view.input;

import javafx.stage.Stage;
import view.InputOperationInterface;
import view.RepaintWorldOperationInterface;

public interface InputerInterface {
    void subscribeToInput(InputOperationInterface moveOnWorld, RepaintWorldOperationInterface repaint);
    void setStageAndAddHandler(Stage stage);
}
