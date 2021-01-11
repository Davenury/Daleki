package model.map;

import view.input.InputParser;

import java.util.*;
import java.util.ArrayList;

public class Field {

    private final int x;
    private final int y;

    public Field(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean equals(Object other){
        if (this == other) {
            return true;
        }
        if (!(other instanceof Field)) {
            return false;
        }
        Field that = (Field) other;
        return Objects.equals(this.x, that.x) && Objects.equals(this.y, that.y);
    }

    public int getX() { return x;}

    public int getY() { return y;}

    public Field addAsVector(Field other){
        return new Field(this.x + other.x, this.y + other.y);
    }

    public Field moveInDirection(Direction direction){
        return this.addAsVector(direction.convertToField());
    }

    public Field moveFromInput(String input){
        Direction direction = InputParser.parseInput(input);
        return this.moveInDirection(direction);
    }

    public boolean moreThan(Field other){
        return this.x >= other.x && this.y >= other.y;
    }

    public boolean lessThan(Field other){
        return this.x <= other.x && this.y <= other.y;
    }

    public List<Field> getFieldsAround(){
        List<Field> fields = new ArrayList<>();
        List<Field> vectors = Arrays.asList(
                new Field(0,1),
                new Field(1,1),
                new Field(1,0),
                new Field(1,-1),
                new Field(0,-1),
                new Field(-1,-1),
                new Field(-1,0),
                new Field(-1,1)
            );
        for(Field vector : vectors){
            fields.add(this.addAsVector(vector));
        }
        return fields;
    }
}
