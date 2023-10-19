package org.peachSpring.app.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users_books")
public class Book_User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private long userId;
    @Column(name = "book_id")
    private long bookId;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Book_User() {
    }


    public Book_User(long userId, long book_id, Date time) {
        this.userId = userId;
        this.bookId = book_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBook_id() {
        return bookId;
    }

    public void setBook_id(long book_id) {
        this.bookId = book_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
