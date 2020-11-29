import model.map.Direction;
import model.map.Doctor;
import model.map.Field;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.NumberOfDocuments;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorTest {

    @Test
    public void DoctorMoveDirection(){
    //given
        Field field = new Field(1,1);
        Doctor doctor = new Doctor(field);
    //when
        doctor.move(Direction.convertInputToDirection("8"));
    //then
        assertEquals(doctor.getField(), new Field(1, 2));
    }
    @Test
    public void DoctorMoveField(){
        //given
        Field field = new Field(1,1);
        Doctor doctor = new Doctor(field);
        //when
        doctor.moveTo(new Field(0,1));
        //then
        assertEquals(doctor.getField(), new Field(1, 2));
    }
}
