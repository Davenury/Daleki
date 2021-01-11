package model.map;

public enum Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, STAY, TELEPORT, UNDO;

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
            case STAY, TELEPORT, UNDO -> new Field(0, 0);
        };
    }

    public static Direction convertInputToDirection(String input){
        input = input.toLowerCase();
        return switch (input){

            case "8", "w" -> Direction.NORTH;
            case "9" -> Direction.NORTH_EAST;
            case "6", "d" -> Direction.EAST;
            case "3" -> Direction.SOUTH_EAST;
            case "2", "s" -> Direction.SOUTH;
            case "1" -> Direction.SOUTH_WEST;
            case "4", "a" -> Direction.WEST;
            case "7" -> Direction.NORTH_WEST;
            case "5", "q" -> Direction.STAY;

            default -> throw new IllegalStateException("You've just invoked static function from Direction class " +
                    "convertInputToDirection with invalid input (string that's not w, a, s, d). If it was supposed " +
                    "to be teleportation, try another function. " +
                    "Didn't you want to use another function?");
        };
    }
}
