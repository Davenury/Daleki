package model.things;

import model.creatures.MapObject;
import model.map.Field;

public class NotSoMovable extends MapObject {
    //Now only a pile of scrap
    // In future probably bonuses to pick - like extra life, etc.
    public NotSoMovable(int x, int y){
        super.setField(new Field(x, y));
    }
}
