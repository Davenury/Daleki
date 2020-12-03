package model.creatures;

import com.google.inject.Injector;
import model.map.Field;

public class DoctorFactory {

    public static Doctor createDoctor(Field field, Injector injector){
        Doctor doctor = injector.getInstance(Doctor.class);
        doctor.setField(field);
        return doctor;
    }

}
