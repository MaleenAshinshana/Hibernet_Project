package lk.ijse.hostel.service.custome.impl;

import lk.ijse.hostel.dao.custome.RoomDAO;
import lk.ijse.hostel.dao.util.DAOFactory;
import lk.ijse.hostel.dao.util.DaoTypes;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.entity.RoomEntity;
import lk.ijse.hostel.service.custome.RoomService;
import lk.ijse.hostel.service.exception.DuplicateException;
import lk.ijse.hostel.service.exception.NotFoundException;
import lk.ijse.hostel.service.util.Convertor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomServiceimpl implements RoomService {
    private final RoomDAO roomDAO ;
    private final Convertor convertor;
    public RoomServiceimpl(){
        roomDAO= (RoomDAO) DAOFactory.getInstance().getDAO(DaoTypes.ROOM);
        convertor=new Convertor();
    }

    @Override
    public boolean saveRoom(RoomDTO roomDTO) throws DuplicateException {
        return roomDAO.save(convertor.toRoom(roomDTO));
    }

    @Override
    public RoomDTO searchRoom(String id) throws NotFoundException {
        Optional<RoomEntity> roomEntity= Optional.ofNullable(roomDAO.search(id));
        if (!roomEntity.isPresent())throw new NotFoundException("Room Not Found");
        return convertor.froRoom(roomEntity.get());
    }

    @Override
    public RoomDTO findType(String type) throws NotFoundException {
        Optional<RoomEntity> roomEntity= Optional.ofNullable(roomDAO.findType(type));
        if (!roomEntity.isPresent())throw new NotFoundException("Room Not Found");
        return convertor.froRoom(roomEntity.get());
    }

    @Override
    public boolean updateRoom(RoomDTO roomDTO) throws NotFoundException {
        return roomDAO.update(convertor.toRoom(roomDTO));
    }

    @Override
    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(id);
    }

    @Override
    public List<RoomDTO> getAllRoom() {
        return roomDAO.getAll().stream().map(roomEntity -> convertor.froRoom(roomEntity)).collect(Collectors.toList());
    }

    @Override
    public long calcAvailableRooms() {
        return roomDAO.calcAvailableRooms();
    }

    @Override
    public boolean qty(String id) {

        return roomDAO.search(id).getQty()>0;
    }
}
