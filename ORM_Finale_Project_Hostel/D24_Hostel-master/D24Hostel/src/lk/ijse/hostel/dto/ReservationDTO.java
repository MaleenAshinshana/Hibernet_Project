package lk.ijse.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;



@ToString
public class ReservationDTO {
    private String id;
    private String date;
    private String status;
    private String student;
    private String room;

    public ReservationDTO() {
    }

    public ReservationDTO(String id, String date, String status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public ReservationDTO(String id, String date, String status, String student, String room) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.student = student;
        this.room = room;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
