package model.map;

public class Field {

    private final int x;
    private final int y;

    public Field(int x, int y){
        this.x = x;
        this.y = y;
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

    public Field add(Field other){
        return new Field(this.x + other.x, this.y + other.y);
    }

    public Field moveInDirection(Direction direction){
        return this.add(direction.convertToField());
    }
}
