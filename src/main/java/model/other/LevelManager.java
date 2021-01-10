package model.other;

import exceptions.GameWonException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LevelManager {
    private IntegerProperty level = new SimpleIntegerProperty(0);

    //Level Property
    public IntegerProperty levelProperty(){
        return level;
    }

    public void setLevelProperty(IntegerProperty level){
        this.level = level;
    }
    public void resetLevel(){
        this.level.set(0);
    }

    public void incrementLevel() throws GameWonException{
        if(this.level.get() < Constants.MAX_LEVEL) {
            this.level.set(this.level.get() + 1);
            System.out.println("LEVEL " + this.level.get());
        }
        else{
            throw new GameWonException();
        }
    }
}
