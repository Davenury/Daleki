import com.google.inject.Guice;
import com.google.inject.Injector;
import exceptions.EndGameException;
import exceptions.GameWonException;
import exceptions.TeleportationTimesException;
import guice.GuiceModule;
import model.creatures.Dalek;
import model.creatures.Doctor;
import model.creatures.DoctorFactory;
import model.creatures.Movable;
import model.map.Direction;
import model.map.Field;
import model.moves.Mover;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MoverTest {

    private Injector injector = Guice.createInjector(new GuiceModule());

    @Test
    public void moveAllDaleksBoomTestAndGameWon()
            throws TeleportationTimesException, EndGameException, GameWonException {
        //given
        Doctor doctor = DoctorFactory.createDoctor(new Field(6,4), injector);
        Dalek dalek1 = new Dalek(doctor, new Field(5, 2));
        Dalek dalek2 = new Dalek(doctor, new Field(7, 2));
        ArrayList<Movable> toMove = new ArrayList<>();
        toMove.add(doctor);
        toMove.add(dalek1);
        toMove.add(dalek2);
        Mover mover = new Mover(11, 11);

        //when then
        assertThrows(GameWonException.class, () -> mover.moveAll(toMove, Direction.NORTH));
    }

    @Test
    public void moveDaleksBoomTestAndTheresPileOfJunkOnTheMap()
            throws TeleportationTimesException, EndGameException, GameWonException {
        //given
        Doctor doctor = DoctorFactory.createDoctor(new Field(6,4), injector);
        Dalek boom1 = new Dalek(doctor, new Field(5, 2));
        Dalek boom2 = new Dalek(doctor, new Field(7, 2));
        ArrayList<Movable> toMove = new ArrayList<>();
        toMove.add(doctor);
        toMove.add(boom1);
        toMove.add(boom2);
        Mover mover = new Mover(11, 11);

        //when
        mover.moveAll(toMove, Direction.NORTH);

        //then
        assertEquals(mover.getMapObjects().size(), 1);
    }
}
