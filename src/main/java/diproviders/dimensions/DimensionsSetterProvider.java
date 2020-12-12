package diproviders.dimensions;

import com.google.inject.Provider;

public class DimensionsSetterProvider implements Provider<IDimensionsSetter> {
    @Override
    public IDimensionsSetter get() {
        int width = 14;
        int height = 15;

        return new DimensionsSetter(width, height);
    }
}
