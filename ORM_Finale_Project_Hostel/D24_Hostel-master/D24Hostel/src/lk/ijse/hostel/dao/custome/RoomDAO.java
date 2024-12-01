package lk.ijse.hostel.dao.custome;

import lk.ijse.hostel.dao.exception.ConstraintViolationException;
import lk.ijse.hostel.dao.util.CrudDAO;
import lk.ijse.hostel.entity.RoomEntity;
import lk.ijse.hostel.entity.StudentEntity;

import java.util.ArrayList;

public interface RoomDAO extends CrudDAO< RoomEntity,String> {
    long calcAvailableRooms();
    ArrayList<String> loadRoomsIds();
    long count();
    ArrayList<String> loadRoomsType();


    //public T search(ID id) throws ConstraintViolationException;

}
