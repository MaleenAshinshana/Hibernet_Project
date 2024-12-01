package lk.ijse.hostel.dao.custome.IMPL;

import lk.ijse.hostel.dao.custome.RoomDAO;
import lk.ijse.hostel.dao.exception.ConstraintViolationException;
import lk.ijse.hostel.entity.RoomEntity;
import lk.ijse.hostel.entity.StudentEntity;
import lk.ijse.hostel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RoomDAOIMPL implements RoomDAO {
    @Override
    public boolean existByPk(String pk) {
        return false;
    }

    @Override
    public boolean save(RoomEntity entity) throws ConstraintViolationException {
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
    public boolean update(RoomEntity entity) throws ConstraintViolationException {Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            session.update(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            RoomEntity std=session.load(RoomEntity.class,id);
            session.delete(std);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    /*@Override
    public boolean deleted(RoomEntity entity) {
        return false;
    }*/

    @Override
    public RoomEntity search(String s) throws ConstraintViolationException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            RoomEntity roomEntity=session.find(RoomEntity.class,s);
            transaction.commit();
            return new RoomEntity(s,roomEntity.getType(),roomEntity.getKey_money(), roomEntity.getQty());
        }catch (Exception e){
            e.printStackTrace();
            /*transaction.rollback();*/
            return null;
        }
    }

    @Override
    public RoomEntity findType(String type) throws ConstraintViolationException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            RoomEntity roomEntity=session.find(RoomEntity.class,type);
            transaction.commit();
            return new RoomEntity(type,roomEntity.getRoom_type_id(),roomEntity.getKey_money(), roomEntity.getQty());
           // return new RoomEntity(s,roomEntity.getType(),roomEntity.getKey_money(), roomEntity.getQty());
        }catch (Exception e){
            e.printStackTrace();
            //transaction.rollback();
            return null;
        }
    }

    @Override
    public List<RoomEntity> getAll() {
        List<RoomEntity> entities;
        try {
            Session session=FactoryConfiguration.getInstance().getSession();
            Query query=session.createQuery("from RoomEntity");
            entities=query.list();
            return  entities;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long calcAvailableRooms() {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction= session.beginTransaction();

        try {
            Long aLong=(Long) session.createQuery("select sum(qty) from RoomEntity ").getSingleResult();
            System.out.println(aLong);
            transaction.commit();
            return aLong;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return 0;
        }

    }

    @Override
    public ArrayList<String> loadRoomsIds() {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction= session.beginTransaction();
        List<RoomEntity> roomEntities=new ArrayList<>();

        try {
            Query query= session.createQuery("SELECT id from RoomEntity ");
            List<String> list=query.list();
            System.out.println(list);
            transaction.commit();
            return(ArrayList<String>) list;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public long count() {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction= session.beginTransaction();

        /*try {
            Query query=session.createQuery("select count (room_type_id) from RoomEntity ");
        }*/
        return 0;
    }

    @Override
    public ArrayList<String> loadRoomsType() {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction= session.beginTransaction();
        List<RoomEntity> roomEntities=new ArrayList<>();

        try {
            Query query= session.createQuery("SELECT type from RoomEntity ");
            List<String> list=query.list();
            System.out.println(list);
            transaction.commit();
            return(ArrayList<String>) list;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }
}

