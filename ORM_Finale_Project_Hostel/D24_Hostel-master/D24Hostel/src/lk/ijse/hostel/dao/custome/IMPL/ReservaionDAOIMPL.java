package lk.ijse.hostel.dao.custome.IMPL;

import lk.ijse.hostel.dao.custome.ReservationDAO;
import lk.ijse.hostel.dao.custome.RoomDAO;
import lk.ijse.hostel.dao.exception.ConstraintViolationException;
import lk.ijse.hostel.dao.util.DAOFactory;
import lk.ijse.hostel.dao.util.DaoTypes;
import lk.ijse.hostel.entity.ReservationEntity;
import lk.ijse.hostel.entity.RoomEntity;
import lk.ijse.hostel.entity.StudentEntity;
import lk.ijse.hostel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ReservaionDAOIMPL implements ReservationDAO {
    private RoomDAO roomDAO;

    public ReservaionDAOIMPL() {

        roomDAO= (RoomDAO) DAOFactory.getInstance().getDAO(DaoTypes.ROOM);
    }

    @Override
    public boolean existByPk(String pk) {
        return false;
    }

    @Override
    public boolean save(ReservationEntity entity) throws ConstraintViolationException {
        Session session= FactoryConfiguration.getInstance().getSession();
        Session session2= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Transaction transaction2=session2.beginTransaction();

        System.out.println(entity);
        try {
            session.save(entity);
            RoomEntity search = roomDAO.search(entity.getRoom().getRoom_type_id());
            search.setQty(search.getQty()-1);
            if (roomDAO.update(search)){
                transaction.commit();
                transaction2.commit();
                return true;
            }else {
                transaction2.rollback();
            }session2.close();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }

    }

    @Override
    public boolean update(ReservationEntity entity) throws ConstraintViolationException {
        Session session=FactoryConfiguration.getInstance().getSession();
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
            session.delete(id);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }



    @Override
    public ReservationEntity search(String s) throws ConstraintViolationException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            ReservationEntity entity=session.find(ReservationEntity.class,s);
            transaction.commit();
            return new ReservationEntity(s, entity.getDate(), entity.getStatus(),new StudentEntity(entity.getStudentEntity().getStudentId()),new RoomEntity(entity.getRoom().getRoom_type_id()));
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public ReservationEntity findType(String type) throws ConstraintViolationException {
        return null;
    }

    @Override
    public List<ReservationEntity> getAll() {

        List<ReservationEntity> entities;
        try {
            Session session=FactoryConfiguration.getInstance().getSession();
            Query query=session.createQuery("from ReservationEntity ");
            entities=query.list();
            return  entities;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        /*Session session=FactoryConfiguration.getInstance().getSession();
        List<ReservationEntity> list;
        try {
            Query query=session.createQuery("from ReservationEntity ");
            list=query.list();
            for (ReservationEntity entity:list) {
                System.out.println(entity.getId());
            }
            System.out.println(list);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }*/

        /*List<ReservationEntity> list;
        try {
             Session session = FactoryConfiguration.getInstance().getSession();
            Query query = session.createQuery("from ReservationEntity ");
            list=query.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
    }
}
