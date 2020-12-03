import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.GuiceModule;
import model.creatures.Doctor;
import model.creatures.DoctorFactory;
import model.map.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoctorFactoryTest {

    private Injector injector = Guice.createInjector(new GuiceModule());

    @Test
    public void doctorFieldTest(){
        //given
        Doctor doctor = DoctorFactory.createDoctor(new Field(5, 5), injector);
        //when
        Field result = doctor.getField();
        //then
        assertEquals(result, new Field(5,5));
    }
}
