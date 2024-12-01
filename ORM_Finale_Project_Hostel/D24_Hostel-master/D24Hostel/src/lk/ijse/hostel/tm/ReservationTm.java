package lk.ijse.hostel.tm;

import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class ReservationTm {

        private String id;
        private String date;
        private String status;
        private String student;
        private String  room ;

        public ReservationTm() {
        }

        public ReservationTm(String id, String date, String status, String student, String room) {
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

        @Override
        public String toString() {
                return "ReservationTm{" +
                        "id='" + id + '\'' +
                        ", date='" + date + '\'' +
                        ", status='" + status + '\'' +
                        ", student='" + student + '\'' +
                        ", room='" + room + '\'' +
                        '}';
        }
}
