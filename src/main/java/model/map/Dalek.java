package model.map;

public class Dalek extends Movable {
    Doctor doctor;

    public Dalek(Doctor doctor, Field field){
        this.doctor = doctor;
        super.field = field;
    }

    @Override
    public String toString(){
        return "Dalek " + super.getField().toString();
    }

    @Override
    public void move(){
        super.moveToField(super.getField().addAsVector(this.calculateNextMove()));
    }

    public Field calculateNextMove(){
        int horizontal_diff = this.getField().getX() - this.doctor.getField().getX();
        int vertical_diff = this.getField().getY() - this.doctor.getField().getY();

        Field horizontal_vector, vertical_vector;

        if (horizontal_diff < 0) {
            horizontal_vector = new Field(1, 0);
        } else if (horizontal_diff == 0){
            horizontal_vector = new Field(0, 0);
        } else {
            horizontal_vector = new Field(-1, 0);
        }

        if (vertical_diff < 0) {
            vertical_vector = new Field(0, 1);
        } else if (vertical_diff == 0){
            vertical_vector = new Field(0, 0);
        } else {
            vertical_vector = new Field(0, -1);
        }

        return vertical_vector.addAsVector(horizontal_vector);
    }
}
