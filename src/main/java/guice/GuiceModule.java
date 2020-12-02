package guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import model.map.World;
import view.input.Inputer;
import view.input.InputerInterface;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputerInterface.class).to(Inputer.class);
    }
}
