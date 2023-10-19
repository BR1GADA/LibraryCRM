package org.peachSpring.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.peachSpring.app.util.constants.Gender;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message =  "Name shouldn't be empty")
    @Size(min = 2, max = 50, message = "Invalid size of name")
    @Column(name = "name")
    @Pattern(regexp = "[a-zA-Zа-яА-Я `.\\-]+" , message = "Name should contains only letters")
    private String name;
    @NotEmpty (message =  "Address shouldn't be empty")
    @Pattern(regexp = "[A-ZА-Я]\\w+, [A-ZА-Я]\\w+, \\d{6}", message = "Your address should be like this: \"Country, City, 000000\"")
    @Column(name = "address")
    private String address;


    @ManyToMany(mappedBy = "users")
    private List<Book> books;

    @Column(name = "hasbook")
    private boolean hasBook;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;


    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.address = email;
    }

    public User(long id, String name, String address, Gender gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isHasBook() {
        return hasBook;
    }

    public void setHasBook(boolean hasBook) {
        this.hasBook = hasBook;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
