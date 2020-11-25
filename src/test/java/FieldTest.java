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
        Field result = field.add(field1);
        //then
        assertEquals(result, new Field(2,2));
    }

    @Test
    public void fieldAddTestAdditionWithNegativeNumbers(){
        //given
        Field field = new Field(5,10);
        Field field1 = new Field(-1,-5);
        //when
        Field result = field.add(field1);
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
}
