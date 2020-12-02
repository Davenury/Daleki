package view.input;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import view.InputOperationInterface;
import view.RepaintWorldOperationInterface;

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

    public void subscribeToInput(InputOperationInterface operation){
        this.userInput
                .map(KeyEvent::getCode)
                .map(KeyCode::getChar)
                .doOnNext(operation::operation)
                .subscribe();
    }

    public void subscribeToInput(InputOperationInterface moveOnWorld, RepaintWorldOperationInterface repaint) {
        this.userInput
                .map(KeyEvent::getCode)
                .map(KeyCode::getChar)
                .doOnNext(moveOnWorld::operation)
                .doOnNext(input -> repaint.operation())
                .subscribe();
    }
}
