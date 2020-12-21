package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import model.creatures.Doctor;
import model.map.Field;
import view.input.Inputer;
import view.input.InputerInterface;

public class GuiceModule extends AbstractModule {

    private static final int WORLD_WIDTH = 11;
    private static final int WORLD_HEIGHT = 11;

    @Override
    protected void configure() {
        bind(InputerInterface.class).to(Inputer.class);
        bind(Integer.class).annotatedWith(Names.named("worldWidth")).toInstance(WORLD_WIDTH);
        bind(Integer.class).annotatedWith(Names.named("worldHeight")).toInstance(WORLD_HEIGHT);
    }

    @Provides
    public Doctor provideDoctor(){
        return new Doctor(new Field(WORLD_WIDTH/2 + 1, WORLD_HEIGHT /2 + 1),
                WORLD_WIDTH, WORLD_HEIGHT);
    }
}
