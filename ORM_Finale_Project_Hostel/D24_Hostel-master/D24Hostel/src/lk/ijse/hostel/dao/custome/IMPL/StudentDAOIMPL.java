package lk.ijse.hostel.dao.custome.IMPL;

import lk.ijse.hostel.dao.custome.StudentDAO;
import lk.ijse.hostel.dao.exception.ConstraintViolationException;
import lk.ijse.hostel.entity.StudentEntity;
import lk.ijse.hostel.util.FactoryConfiguration;
import lk.ijse.hostel.util.Navigation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOIMPL implements StudentDAO {
    @Override
    public boolean existByPk(String pk) {
        return false;
    }

    @Override
    public boolean save(StudentEntity entity) throws ConstraintViolationException {
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

        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(StudentEntity entity) throws ConstraintViolationException {
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
        }finally {
            session.close();
        }

    }

    @Override
    public boolean delete(String id) {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            StudentEntity std=session.load(StudentEntity.class,id);
            session.delete(std);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }

    }

/*    @Override
    public boolean deleted(StudentEntity entity) {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            session.delete(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }

    }*/

    @Override
    public StudentEntity search(String s) throws ConstraintViolationException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            StudentEntity studentEntity=session.find(StudentEntity.class,s);
            transaction.commit();
            return new StudentEntity(s,studentEntity.getStudentName(),studentEntity.getAddress(),studentEntity.getContact_number(),studentEntity.getDate_of_birth(),studentEntity.getGender());
        }catch (Exception e){
            e.printStackTrace();
            //transaction.rollback();
            return null;
        }finally {
            session.close();
        }

    }

    @Override
    public StudentEntity findType(String type) throws ConstraintViolationException {
        return null;
    }

    @Override
    public List<StudentEntity> getAll() {
        Session session=FactoryConfiguration.getInstance().getSession();
        List<StudentEntity> list;
        try {
            Query query=session.createQuery("from StudentEntity ");
            list=query.list();
            for (StudentEntity studentEntity:list) {
                System.out.println(studentEntity.getStudentId());
            }
            System.out.println(list);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long calcAllStudent() {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction= session.beginTransaction();

        try {
            Long aLong=(Long) session.createQuery("SELECT COUNT (*) FROM StudentEntity ").getSingleResult();
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
    public ArrayList<String> loadStudentIdS() {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction= session.beginTransaction();
        List<StudentEntity> studentEntityList=new ArrayList<>();

        try {
            Query query= session.createQuery("SELECT id from StudentEntity ");
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
        return 0;
    }
}
