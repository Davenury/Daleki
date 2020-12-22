import com.google.inject.Inject;
import model.map.Direction;
import model.creatures.Doctor;
import model.map.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorTest {

    @Test
    public void DoctorMoveDirection(){
    //given
        Doctor doctor = new Doctor(new Field(0, 0), 11, 11);
        Field field = new Field(1,1);
        doctor.setField(field);
    //when
        doctor.move(Direction.convertInputToDirection("8"));
    //then
        assertEquals(doctor.getField(), new Field(1, 2));
    }
    @Test
    public void DoctorMoveField(){
        //given
        Doctor doctor = new Doctor(new Field(0, 0), 11, 11);
        Field field = new Field(1,1);
        doctor.setField(field);
        //when
        doctor.moveTo(new Field(0,1));
        //then
        assertEquals(doctor.getField(), new Field(1, 2));
    }
}
