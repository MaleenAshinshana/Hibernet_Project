package lk.ijse.hostel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Data
@ToString
@Table(name = "user")
public class UserEntity implements SuperEntity{
    @Id
    private String id;
    private String name;
    private String password;

    public UserEntity() {
    }

    public UserEntity(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
