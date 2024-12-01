package lk.ijse.hostel.service.custome.impl;

import lk.ijse.hostel.dao.custome.StudentDAO;
import lk.ijse.hostel.dao.util.DAOFactory;
import lk.ijse.hostel.dao.util.DaoTypes;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.StudentEntity;
import lk.ijse.hostel.service.custome.StudentService;
import lk.ijse.hostel.service.exception.DuplicateException;
import lk.ijse.hostel.service.exception.NotFoundException;
import lk.ijse.hostel.service.util.Convertor;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceIMPL implements StudentService {
    private final StudentDAO studentDAO;
    private final Convertor convertor;
    public StudentServiceIMPL(){
        studentDAO= (StudentDAO) DAOFactory.getInstance().getDAO(DaoTypes.STUDENT);
        convertor=new Convertor();
    }
    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws DuplicateException {
        System.out.println(studentDTO+"");
        return studentDAO.save(new StudentEntity(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getContact_number(),studentDTO.getDate_of_birth(),studentDTO.getGender()));

    }

    @Override
    public StudentDTO searchStudent(String id) throws NotFoundException {
        Optional<StudentEntity>studentEntity= Optional.ofNullable(studentDAO.search(id));
        if (!studentEntity.isPresent())throw new NotFoundException("Student No Found") ;
        return convertor.fromStudent(studentEntity.get());
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws NotFoundException {
        return studentDAO.update(new StudentEntity(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getContact_number(),studentDTO.getDate_of_birth(),studentDTO.getGender()));
    }

    @Override
    public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        //return studentDAO.deleted(convertor.toStudent(studentDTO));
        return studentDAO.delete(id);
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        System.out.println("***********");
        return studentDAO.getAll().stream().map(student -> convertor.fromStudent(student)).collect(Collectors.toList());
        //return studentDAO.getAll().stream().map(studentEntity-> convertor.fromStudent(studentEntity)).collect(Collectors.toList());
        //return studentDAO.getAll().stream().map(student -> new StudentDTO(student.getStudentId(),student.getStudentName(),student.getAddress(),student.getContact_number(),student.getDate_of_birth(),student.getGender())).collect(Collectors.toList());
        //return studentDAO.getAll().stream().map(entity -> convertor.fromStudent(entity)).collect(Collectors.toList());

    }

    @Override
    public long calcAllStudent() {
        return studentDAO.calcAllStudent();
    }
}
