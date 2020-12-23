package exceptions;

public class CantUndoException extends Exception{
    public CantUndoException(){
        super("Can't undo!");
    }
}
