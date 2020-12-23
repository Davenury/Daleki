package exceptions;

import model.creatures.Movable;
import java.util.*;
public class UndoException extends Exception {
    private List<Movable> movables;

    public UndoException(List<Movable> movables){
        super("Undo!");
        this.movables = movables;
    }

    public List<Movable> getMovables() {
        return movables;
    }
}
