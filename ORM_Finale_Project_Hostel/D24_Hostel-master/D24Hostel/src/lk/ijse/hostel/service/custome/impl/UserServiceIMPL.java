package lk.ijse.hostel.service.custome.impl;


import lk.ijse.hostel.dao.custome.UserDAO;
import lk.ijse.hostel.dao.util.DAOFactory;
import lk.ijse.hostel.dao.util.DaoTypes;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.entity.UserEntity;
import lk.ijse.hostel.service.custome.UserService;
import lk.ijse.hostel.service.exception.DuplicateException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
/*import lk.ijse.hostel.service.util.Convertor;*/


public class UserServiceIMPL implements UserService {
    private final UserDAO userDAO;
    /*private final Convertor convertor;*/
    public UserServiceIMPL(){
        userDAO= (UserDAO) DAOFactory.getInstance().getDAO(DaoTypes.USER);
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws DuplicateException {
        //userDAO.save(convertor.toUser(userDTO));
        return userDAO.save(new UserEntity(userDTO.getId(), userDTO.getName(), userDTO.getPassword()));
    }

    @Override
    public boolean login(UserDTO userDTO) throws IOException {
     UserEntity userEntity=userDAO.search(userDTO.getName());
        return userEntity!=null&& userEntity.getPassword().equals(userDTO.getPassword());
    }

    @Override
    public List<UserDTO> getAll() {
        return null;
        //return userDAO.getAll().stream().map(userEntity -> convertor.f(userEntity)).collect(Collectors.toList());
       // return userDAO.getAll().stream().map(userEntity -> convw.froRoom(roomEntity)).collect(Collectors.toList());
       // return userDAO.getAll().stream().map(userEntity -> );
    }

}
