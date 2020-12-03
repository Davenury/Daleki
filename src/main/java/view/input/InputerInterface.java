package view.input;

import javafx.stage.Stage;

public interface InputerInterface {
    void subscribeToInput(StringOperationInterface moveOnWorld, VoidOperationInterface repaint);
    void setStageAndAddHandler(Stage stage);
    void subscribeToInput(StringOperationInterface operation);
}
