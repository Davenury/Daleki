package view.input;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Inputer implements InputerInterface{
    public PublishSubject<KeyEvent> userInput;
    private Stage stage;

    public Inputer(){
        this.userInput = PublishSubject.create();
    }

    public void setStageAndAddHandler(Stage stage){
        this.stage = stage;
        this.addKeyEventHandler();
    }

    private void addKeyEventHandler(){
        this.stage.addEventHandler(KeyEvent.KEY_PRESSED, (key)->{
            userInput.onNext(key);
        });
    }

    public void subscribeToInput(StringOperationInterface operation){
        this.userInput
                .map(KeyEvent::getCode)
                .map(KeyCode::getChar)
                .doOnNext(operation::operation)
                .subscribe();
    }

    public void subscribeToInput(StringOperationInterface moveOnWorld, VoidOperationInterface repaint) {
        this.userInput
                .map(KeyEvent::getCode)
                .map(KeyCode::getChar)
                .doOnNext(moveOnWorld::operation)
                .doOnNext(input -> repaint.operation())
                .subscribe();
    }
}
