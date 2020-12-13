package exceptions;

public class EndGameException extends Exception{
    public EndGameException(){
        super("Game lost");
    }
}
