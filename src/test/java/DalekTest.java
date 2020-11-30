import model.creatures.Dalek;
import model.creatures.Doctor;
import model.map.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DalekTest {

    @Test
    public void DalekisMoveToDoctor(){
        //given
        Field field = new Field(0,0);

        Field[] fields = {
        new Field(0,2),  //N
        new Field(2,2),  //NE
        new Field(2,0),  //E
        new Field(2,-2), //SE
        new Field(0,-2), //S
        new Field(-2,-2),//SW
        new Field(-2,0), //W
        new Field(-2,2)};//NW

        Doctor doctor = new Doctor(field);

        Dalek[] daleks = new Dalek[8];
        for (int i = 0; i<8; i++){
            daleks[i] = new Dalek(doctor, fields[i]);
        }

        //when
        for (int i = 0; i<8; i++){
            daleks[i].move();
        }

        //then
        Field[] expectedFields = {
                new Field(0,1),  //N
                new Field(1,1),  //NE
                new Field(1,0),  //E
                new Field(1,-1), //SE
                new Field(0,-1), //S
                new Field(-1,-1),//SW
                new Field(-1,0), //W
                new Field(-1,1)};//NW

        for (int i = 0; i<8; i++){
            assertEquals(daleks[i].getField(), expectedFields[i]);
        }
    }
}
