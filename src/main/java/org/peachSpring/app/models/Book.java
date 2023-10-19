package org.peachSpring.app.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    private final static int CURRENT_YEAR = 2023;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    @NotEmpty(message = "name shouldn`t be empty")
    @Pattern(regexp = "[a-zA-Zа-яА-Я `:.\\-]+" , message = "Name should contains only letters")
    private String name;
    @Column(name = "year")
    @Positive(message = "year should be greater than 0")
    @Max(value = CURRENT_YEAR, message = " Year should be less than " + CURRENT_YEAR)
    private int year;
    @Column(name = "author")
    @Size(min = 2, max = 50, message = "Invalid size of name")
    @NotEmpty (message = "author shouldn`t be empty")
    @Pattern(regexp = "[a-zA-Zа-яА-Я `.\\-]+" , message = " Authors name should contains only letters")
    private String author;


    @Column(name = "istaken")
    private boolean istaken;

    @ManyToMany
    @JoinTable(name = "users_books",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Book(String name, int year, String author) {
        this.name = name;
        this.year = year;
        this.author = author;
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isIstaken() {
        return istaken;
    }

    public void setIstaken(boolean istaken) {
        this.istaken = istaken;
    }
}
