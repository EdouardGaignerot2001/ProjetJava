package com.librarymanagement;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.Writer;

public class User {
    private String GUID;
    private String name;
    private List<String> rentedBooks;

    public User(String id, String name) {
        this.GUID = UUID.randomUUID().toString();
        this.name = name;
        this.rentedBooks = new ArrayList<>();
    }

    public String getGUID() {
        return GUID;
    }

    public String getName() {
        return name;
    }

    public List<String> getRentedBooks() {
        return rentedBooks;
    }

    public void rentBook(String bookGUID) {
        rentedBooks.add(bookGUID);
    }

    public boolean hasReachedMaxBooks() {
        return rentedBooks.size() >= 3;
    }

    public boolean alreadyRentedBook(String bookGUID) {
        return rentedBooks.contains(bookGUID);
    }

    public void saveToJson(String jsonFileName, List<User> users) {

        try (Writer writer = new FileWriter(jsonFileName)) {
            Gson gson = new Gson();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
