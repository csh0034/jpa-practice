package study.rawjpa.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMT_ACCOUNT")
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    public Account(String email) {
        this.email = email;
    }
}
