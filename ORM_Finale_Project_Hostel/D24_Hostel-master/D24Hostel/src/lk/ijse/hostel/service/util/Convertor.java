package lk.ijse.hostel.service.util;

import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.entity.ReservationEntity;
import lk.ijse.hostel.entity.RoomEntity;
import lk.ijse.hostel.entity.StudentEntity;
import lk.ijse.hostel.entity.UserEntity;

import java.sql.Date;

public class Convertor {
    public StudentDTO fromStudent(StudentEntity studentEntity){
        System.out.println(studentEntity.toString());
        return new StudentDTO(studentEntity.getStudentId(),studentEntity.getStudentName(),studentEntity.getAddress(),studentEntity.getContact_number(),studentEntity.getDate_of_birth(),studentEntity.getGender());

    }
    public StudentEntity toStudent(StudentDTO studentDTO){
        System.out.println(studentDTO.toString());
        return new StudentEntity(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getContact_number(),studentDTO.getDate_of_birth(),studentDTO.getGender());

    }
    public RoomDTO froRoom(RoomEntity roomEntity){
        System.out.println(roomEntity+"******");
        return new RoomDTO(roomEntity.getRoom_type_id(), roomEntity.getType(),roomEntity.getKey_money(), roomEntity.getQty());
    }
    public RoomEntity toRoom(RoomDTO roomDTO){
        return new RoomEntity(roomDTO.getRoom_type_id(),roomDTO.getType(),roomDTO.getKey_money(), roomDTO.getQty());
    }

    public ReservationDTO fromReservation(ReservationEntity entity){
        //System.out.println(entity);
        return new ReservationDTO(entity.getId(), entity.getDate(), entity.getStatus(),
                entity.getStudentEntity().getStudentId(),entity.getRoom().getRoom_type_id());
    }
    public ReservationEntity toReservation(ReservationDTO dto){
        return new ReservationEntity(dto.getId(), dto.getDate(), dto.getStatus(),
                new StudentEntity(dto.getStudent()),new RoomEntity(dto.getRoom()));
    }
    public UserDTO fromUser(UserEntity userEntity){
        return new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getPassword());
    }
    public UserEntity toUser(UserDTO userDTO){
        return new UserEntity(userDTO.getId(), userDTO.getName(), userDTO.getPassword());
    }
}
