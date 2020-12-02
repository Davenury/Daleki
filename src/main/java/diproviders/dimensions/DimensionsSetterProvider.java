package diproviders.dimensions;

import com.google.inject.Provider;

public class DimensionsSetterProvider implements Provider<IDimensionsSetter> {
    @Override
    public IDimensionsSetter get() {
        int width = 11;
        int height = 11;

        return new DimensionsSetter(width, height);
    }
}
