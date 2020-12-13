package diproviders.dimensions;

import com.google.inject.Provider;

public class DimensionsSetterProvider implements Provider<IDimensionsSetter> {
    @Override
    public IDimensionsSetter get() {
        int width = 10;
        int height = 10;

        return new DimensionsSetter(width, height);
    }
}
