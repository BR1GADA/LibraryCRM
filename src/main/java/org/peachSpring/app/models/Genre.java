package org.peachSpring.app.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GeneratedColumn;

import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "genre_name")
    private String genreName;

    @OneToMany(mappedBy = "genre")
    private List<Book> books;

    public Genre() {
    }

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return genreName;
    }
}
