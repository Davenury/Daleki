package model.map;

public enum Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    public Direction next(){
        return getDirection(NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH);
    }

    public Direction previous(){
        return getDirection(NORTH_WEST, NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST);
    }

    private Direction getDirection(Direction northWest, Direction north, Direction northEast,
                                   Direction east, Direction southEast, Direction south,
                                   Direction southWest, Direction west) {
        return switch (this) {
            case NORTH -> northWest;
            case NORTH_EAST -> north;
            case EAST -> northEast;
            case SOUTH_EAST -> east;
            case SOUTH -> southEast;
            case SOUTH_WEST -> south;
            case WEST -> southWest;
            case NORTH_WEST -> west;
        };
    }

    public Field convertToField(){
        return switch(this){
            case NORTH -> new Field(0, 1);
            case NORTH_EAST -> new Field(1, 1);
            case EAST -> new Field(1, 0);
            case SOUTH_EAST -> new Field(1, -1);
            case SOUTH -> new Field(0, -1);
            case SOUTH_WEST -> new Field(-1, -1);
            case WEST -> new Field(-1, 0);
            case NORTH_WEST -> new Field(-1, 1);
        };
    }

    public static Direction convertInputToDirection(String input){
        input = input.toLowerCase();
        return switch (input){
            case "s" -> Direction.SOUTH;
            case "w" -> Direction.NORTH;
            case "d" -> Direction.EAST;
            case "a" -> Direction.WEST;
            default -> throw new IllegalStateException("You've just invoked static function from Direction class " +
                    "convertInputToDirection with invalid input (string that's not w, a, s, d). If it was supposed " +
                    "to be teleportation, try another function. " +
                    "Didn't you want to use another function?");
        };
    }
}