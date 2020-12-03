package view.input;

import io.reactivex.rxjava3.subjects.PublishSubject;
import model.map.Direction;

public class InputParser {

    public static PublishSubject<Boolean> input = PublishSubject.create();

    public static void subscribeToInputParser(VoidOperationInterface operation){
        input.doOnNext(input -> operation.operation())
            .subscribe();
    }

    public static Direction parseInput(String input) throws IllegalStateException{
        input = input.toLowerCase();
        switch (input) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "w", "s", "d" -> {
                return Direction.convertInputToDirection(input);
            }
            case "t" -> {
                InputParser.input.onNext(true);
                return Direction.TELEPORT;
            }
            default -> throw new IllegalStateException("Not right input to parseInput method!");
        }
    }
}
