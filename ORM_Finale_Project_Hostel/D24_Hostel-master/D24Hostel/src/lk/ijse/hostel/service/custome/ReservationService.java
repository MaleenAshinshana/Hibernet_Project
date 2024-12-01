package lk.ijse.hostel.service.custome;

import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.service.SuperSevice;
import lk.ijse.hostel.service.exception.DuplicateException;
import lk.ijse.hostel.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReservationService extends SuperSevice {
    boolean saveReservatoin(ReservationDTO reservationDTO) throws DuplicateException;

    ArrayList<String> loadAllStudentIds() throws SQLException,ClassNotFoundException;

    ArrayList<String> loadRoomTypeID()throws SQLException,ClassNotFoundException;
    public List<ReservationDTO> getAll();
    ArrayList<String> loadRoomsType();
    ReservationDTO searchReservation(String id) throws NotFoundException;
    boolean updateReservation(ReservationDTO reservationDTO) throws NotFoundException;
}
