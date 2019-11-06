package pl.edu.pjwstk.jaz.auth;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String encryptedPassword;
    private String email;
    private Date birthDate;

    public ProfileEntity(String name, String surname, String username, String encryptedPassword, String email, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        this.birthDate = birthDate;
    }

    public ProfileEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return name + "\n" + surname + "\n" + username + "\n" + encryptedPassword + "\n" + birthDate;
    }
}
