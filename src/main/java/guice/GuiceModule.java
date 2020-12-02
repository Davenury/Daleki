package guice;

import com.google.inject.AbstractModule;
import diproviders.dimensions.DimensionsSetterProvider;
import diproviders.dimensions.IDimensionsSetter;
import view.input.Inputer;
import view.input.InputerInterface;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputerInterface.class).to(Inputer.class);
        bind(IDimensionsSetter.class).toProvider(DimensionsSetterProvider.class);
    }
}
