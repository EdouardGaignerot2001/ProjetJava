package com.librarymanagement;

import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String GUID;
    private String name;
    private List<Book> rentedBooks;
    private List<User> users;


    public User(String id, String name) {
        this.GUID =  UUID.randomUUID().toString();
        this.name = name;
        this.rentedBooks = new ArrayList<>(); // Initialise rentedBooks
    }
   
    public String getId() {
        return GUID;
    }

    public String getName() {
        return name;
    }

    public List<Book> getRentedBooks() {
        if (rentedBooks == null) { // Vérification de nullité au cas où
            rentedBooks = new ArrayList<>();
        }
        return rentedBooks;
    }

    public void rentBook(Book book) {
        getRentedBooks().add(book); // Assure rentedBooks non null avant l'ajout
    }

    public boolean hasReachedMaxBooks() {
        return getRentedBooks().size() >= 3; // Utilise getRentedBooks() pour éviter NullPointer
    }

    public boolean alreadyRentedBook(Book book) {
        return getRentedBooks().contains(book); // Utilise getRentedBooks() pour éviter NullPointer
    }
}
