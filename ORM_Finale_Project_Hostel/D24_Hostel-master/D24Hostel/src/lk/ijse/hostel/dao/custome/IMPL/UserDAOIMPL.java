package lk.ijse.hostel.dao.custome.IMPL;

import lk.ijse.hostel.dao.custome.UserDAO;
import lk.ijse.hostel.dao.exception.ConstraintViolationException;
import lk.ijse.hostel.entity.RoomEntity;
import lk.ijse.hostel.entity.UserEntity;
import lk.ijse.hostel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOIMPL implements UserDAO {
    @Override
    public boolean existByPk(String pk) {
        return false;
    }

    @Override
    public boolean save(UserEntity entity) throws ConstraintViolationException {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            session.save(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }

    }

    @Override
    public boolean update(UserEntity entity) throws ConstraintViolationException {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

   /* @Override
    public boolean deleted(UserEntity entity) {
        return false;
    }*/

    @Override
    public UserEntity search(String s) throws ConstraintViolationException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            UserEntity userEntity=session.get(UserEntity.class,s);
            transaction.commit();
            return userEntity;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public UserEntity findType(String type) throws ConstraintViolationException {
        return null;
    }

    @Override
    public List<UserEntity> getAll()  {
        List<UserEntity> entities;
        try {
            Session session=FactoryConfiguration.getInstance().getSession();
            Query query=session.createQuery("from UserEntity ");
            entities=query.list();
            return  entities;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        /*List<UserEntity> entities;
        Session  session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();


        try {
            Query from_userEntity_ = session.createQuery("from UserEntity ");
            entities=from_userEntity_.list();
            return (ArrayList<UserEntity>) entities;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }*/
    }
}
