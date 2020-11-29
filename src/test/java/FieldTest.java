import model.map.Direction;
import model.map.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    @Test
    public void fieldEqualsTestEquals(){
        //given
        Field field = new Field(1,1);
        Field field2 = new Field(1,1);
        //when then
        assertEquals(field, field2);
    }

    @Test
    public void fieldEqualsTestNotEqualsY(){
        //given
        Field field = new Field(1,1);
        Field field2 = new Field(1,2);
        //when then
        assertNotEquals(field, field2);
    }

    @Test
    public void fieldEqualsTestNotEqualsX(){
        //given
        Field field = new Field(1,1);
        Field field2 = new Field(2,1);
        //when then
        assertNotEquals(field, field2);
    }

    @Test
    public void fieldEqualsTestNotEqualsAtAll(){
        //given
        Field field = new Field(1,1);
        Field field2 = new Field(2,2);
        //when then
        assertNotEquals(field, field2);
    }

    @Test
    public void fieldAddTestOnlyPositiveNumbers(){
        //given
        Field field = new Field(1,1);
        Field field1 = new Field(1,1);
        //when
        Field result = field.addAsVector(field1);
        //then
        assertEquals(result, new Field(2,2));
    }

    @Test
    public void fieldAddTestAdditionWithNegativeNumbers(){
        //given
        Field field = new Field(5,10);
        Field field1 = new Field(-1,-5);
        //when
        Field result = field.addAsVector(field1);
        //then
        assertEquals(result, new Field(4,5));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToNorth(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.NORTH);
        //then
        assertEquals(moved, new Field(1,2));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToEast(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.EAST);
        //then
        assertEquals(moved, new Field(2,1));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToSouth(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.SOUTH);
        //then
        assertEquals(moved, new Field(1,0));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToWest(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.WEST);
        //then
        assertEquals(moved, new Field(0,1));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToNorthEast(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.NORTH_EAST);
        //then
        assertEquals(moved, new Field(2,2));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToNorthWest(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.NORTH_WEST);
        //then
        assertEquals(moved, new Field(0,2));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToSouthEast(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.SOUTH_EAST);
        //then
        assertEquals(moved, new Field(2,0));
    }

    @Test
    public void fieldMoveInDirectionTestMoveToSouthWest(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveInDirection(Direction.SOUTH_WEST);
        //then
        assertEquals(moved, new Field(0,0));
    }

    @Test
    public void fieldMoveFromInputTestMoveToW(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("w");
        //then
        assertEquals(moved, new Field(1,2));
    }

    @Test
    public void fieldMoveFromInputTestMoveToD(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("d");
        //then
        assertEquals(moved, new Field(2,1));
    }

    @Test
    public void fieldMoveFromInputTestMoveToS(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("s");
        //then
        assertEquals(moved, new Field(1,0));
    }

    @Test
    public void fieldMoveFromInputTestMoveToA(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("a");
        //then
        assertEquals(moved, new Field(0,1));
    }

    @Test
    public void fieldMoveFromInputTestMoveToWBigLetter(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("W");
        //then
        assertEquals(moved, new Field(1,2));
    }

    @Test
    public void fieldMoveFromInputTestMoveToDBigLetter(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("D");
        //then
        assertEquals(moved, new Field(2,1));
    }

    @Test
    public void fieldMoveFromInputTestMoveToSBigLetter(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("S");
        //then
        assertEquals(moved, new Field(1,0));
    }

    @Test
    public void fieldMoveFromInputTestMoveToABigLetter(){
        //given
        Field field = new Field(1,1);
        //when
        Field moved = field.moveFromInput("A");
        //then
        assertEquals(moved, new Field(0,1));
    }

    @Test
    public void fieldMoveFromInputInvalidInput(){
        //given
        Field field = new Field(1, 1);
        //when
        String input = "f";
        //then
        assertThrows(IllegalStateException.class, () -> field.moveFromInput(input));
    }

    @Test
    public void fieldMoreThanTestMoreThan(){
        //given
        Field filed = new Field(5,10);
        //when
        Field field2 = new Field(0, 0);
        //then
        assertTrue(filed.moreThan(field2));
    }

    @Test
    public void fieldMoreThanTestNotMoreThan(){
        //given
        Field filed = new Field(0,0);
        //when
        Field field2 = new Field(10, 10);
        //then
        assertFalse(filed.moreThan(field2));
    }

    @Test
    public void fieldMoreThanTestXMoreThan(){
        //given
        Field filed = new Field(5,0);
        //when
        Field field2 = new Field(0, 0);
        //then
        assertTrue(filed.moreThan(field2));
    }

    @Test
    public void fieldMoreThanTestYMoreThan(){
        //given
        Field filed = new Field(0,10);
        //when
        Field field2 = new Field(0, 0);
        //then
        assertTrue(filed.moreThan(field2));
    }

    @Test
    public void fieldLessThanTestMoreThan(){
        //given
        Field filed = new Field(5,10);
        //when
        Field field2 = new Field(10, 12);
        //then
        assertTrue(filed.lessThan(field2));
    }

    @Test
    public void fieldLessThanTestNotLessThan(){
        //given
        Field filed = new Field(10,10);
        //when
        Field field2 = new Field(0, 0);
        //then
        assertFalse(filed.lessThan(field2));
    }

    @Test
    public void fieldLessThanTestXLessThan(){
        //given
        Field filed = new Field(0,0);
        //when
        Field field2 = new Field(5, 0);
        //then
        assertTrue(filed.lessThan(field2));
    }

    @Test
    public void fieldLessThanTestYLessThan(){
        //given
        Field filed = new Field(0,0);
        //when
        Field field2 = new Field(0, 10);
        //then
        assertTrue(filed.lessThan(field2));
    }

}
