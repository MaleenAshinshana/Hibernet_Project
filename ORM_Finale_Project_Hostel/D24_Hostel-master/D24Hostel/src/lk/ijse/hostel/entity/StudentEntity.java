package lk.ijse.hostel.entity;

import lk.ijse.hostel.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "student")
public class StudentEntity implements SuperEntity{
    @Id
    private String studentId;
    private String StudentName;
    private String address;
    private int contact_number;
    private String date_of_birth;
    private String gender;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations=new ArrayList<>();


    public StudentEntity(String studentId, String studentName, String address, int contact_number, String date_of_birth, String gender) {
        this.studentId = studentId;
        StudentName = studentName;
        this.address = address;
        this.contact_number = contact_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
    }

    public StudentEntity(String studentId) {
        this.studentId = studentId;
    }
}
