package org.peachSpring.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.peachSpring.app.util.constants.Gender;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 50, message = "Invalid size of name")
    @Column(name = "name")
    @Pattern(regexp = "[a-zA-Zа-яА-Я `.\\-]+", message = "Name should contains only letters")
    private String name;
    @Email
    @Column(name = "email")
    private String email;


    @ManyToMany(mappedBy = "users")
    private List<Book> books;

    @Column(name = "ishaspass")
    private boolean isHasPass = true;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Column(name = "login")
    @Size(min = 4, max = 30, message = "Login`s length should be bigger than 4, and less than 30")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    @Column(name = "image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(long id, String name, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(long id, String name, String email, boolean isHasPass, Gender gender, String login, String password, String role, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isHasPass = isHasPass;
        this.gender = gender;
        this.login = login;
        this.password = password;
        this.role = role;
        this.image = image;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasPass() {
        return isHasPass;
    }

    public void setHasPass(boolean hasPass) {
        this.isHasPass = hasPass;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getName().equals(user.getName()) && Objects.equals(getEmail(), user.getEmail()) && getGender() == user.getGender() && getLogin().equals(user.getLogin()) && getRole().equals(user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGender());
    }

}
