package model.map;

public class Doctor extends Movable {

    public Doctor(Field field){
        super.field = field;
    }
    @Override
    public String toString(){
        return "Doctor " + super.getField().toString();
    }

    @Override
    public void move(Direction direction){
        super.updateField(super.getField().addAsVector(direction.convertToField()));
    }

    @Override
    public void move(Field field){
        super.updateField(super.getField().addAsVector(field));
    }

    public void teleport(){
        //TODO
        // teleport away from daleks
        // We don't know yet how to store the daleks and how to pick their positions
        // And no size of board etc.
    }

}
