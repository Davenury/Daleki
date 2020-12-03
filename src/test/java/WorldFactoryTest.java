import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.GuiceModule;
import model.map.World;
import model.map.WorldFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorldFactoryTest {

    private Injector injector = Guice.createInjector(new GuiceModule());

    @Test
    public void getWorldWidthTest(){
        //given
        World world = WorldFactory.createWorld(injector);
        //when
        int result = world.getHeight();
        //then
        assertEquals(result, 11);
    }
}
