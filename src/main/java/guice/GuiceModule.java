package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import model.creatures.Doctor;
import model.map.Field;
import view.input.Inputer;
import view.input.InputerInterface;

public class GuiceModule extends AbstractModule {

    private final int worldWidth = 11;
    private final int worldHeight = 11;

    @Override
    protected void configure() {
        bind(InputerInterface.class).to(Inputer.class);
        bind(Integer.class).annotatedWith(Names.named("worldWidth")).toInstance(worldWidth);
        bind(Integer.class).annotatedWith(Names.named("worldHeight")).toInstance(worldHeight);
    }

    @Provides
    public Doctor provideDoctor(){
        return new Doctor(new Field(this.worldWidth/2 + 1, this.worldHeight/2 + 1),
                this.worldWidth, this.worldHeight);
    }
}
