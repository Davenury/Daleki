import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.GuiceModule;
import model.creatures.DoctorFactory;
import model.map.Direction;
import model.creatures.Doctor;
import model.map.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorTest {

    private Injector injector = Guice.createInjector(new GuiceModule());

    @Test
    public void DoctorMoveDirection(){
    //given
        Field field = new Field(1,1);
        Doctor doctor = DoctorFactory.createDoctor(field, injector);
        doctor.setField(field);
    //when
        doctor.move(Direction.convertInputToDirection("8"));
    //then
        assertEquals(doctor.getField(), new Field(1, 2));
    }
    @Test
    public void DoctorMoveField(){
        //given
        Field field = new Field(1,1);
        Doctor doctor = DoctorFactory.createDoctor(field, injector);
        doctor.setField(field);
        //when
        doctor.moveTo(new Field(0,1));
        //then
        assertEquals(doctor.getField(), new Field(1, 2));
    }
}
