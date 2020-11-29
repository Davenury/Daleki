package model.map;

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
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Field))
            return false;
        Field that = (Field) other;
        return ((this.x == that.x) && (this.y==that.y));
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
        return this.moveInDirection(Direction.convertInputToDirection(input));
    }

    public boolean isADirection(){
        if (this.x <=1 && this.x >=-1 && this.y <=1 && this.y >=-1) return true;
        return false;
    }

    public boolean moreThan(Field other){
        return this.x >= other.x && this.y >= other.y;
    }

    public boolean lessThan(Field other){
        return this.x <= other.x && this.y <= other.y;
    }
}
