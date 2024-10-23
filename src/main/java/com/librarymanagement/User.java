package com.librarymanagement;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Book> rentedBooks;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.rentedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void rentBook(Book book) {
        rentedBooks.add(book);
    }

    public boolean hasReachedMaxBooks() {
        return rentedBooks.size() >= 3;
    }

    public boolean alreadyRentedBook(Book book) {
        return rentedBooks.contains(book);
    }
}
