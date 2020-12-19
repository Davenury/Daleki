package exceptions;

public class DoctorDiesException extends Exception{
    public DoctorDiesException(){
        super("You die");
    }
}