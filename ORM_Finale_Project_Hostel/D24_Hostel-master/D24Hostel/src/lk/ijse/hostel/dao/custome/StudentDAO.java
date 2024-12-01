package lk.ijse.hostel.dao.custome;

import lk.ijse.hostel.dao.util.CrudDAO;
import lk.ijse.hostel.entity.StudentEntity;
import lk.ijse.hostel.entity.UserEntity;

import java.util.ArrayList;

public interface StudentDAO extends CrudDAO<StudentEntity,String> {

    long calcAllStudent();
    ArrayList<String> loadStudentIdS();
    long count();
}
