package exceptions;

public class GameWonException extends Exception{
    public GameWonException(){
        super("Game won");
    }
}
