package diproviders.dimensions;

import com.google.inject.Inject;

public class DimensionsSetter implements IDimensionsSetter {

    private int width;
    private int height;

    @Inject
    public DimensionsSetter(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
