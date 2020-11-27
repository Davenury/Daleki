package view;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Inputer {
    public PublishSubject<KeyEvent> userInput;
    private final Stage stage;

    public Inputer(Stage stage){
        this.stage = stage;
        this.userInput = PublishSubject.create();
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
}
