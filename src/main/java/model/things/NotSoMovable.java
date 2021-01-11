package model.things;

import model.creatures.MapObject;
import model.map.Field;

public class NotSoMovable extends MapObject {
    public NotSoMovable(int x, int y){
        super(new Field(x, y));
    }
}
