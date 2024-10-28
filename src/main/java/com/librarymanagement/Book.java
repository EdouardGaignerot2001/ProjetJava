package com.librarymanagement;

import java.util.UUID;

public class Book {
    private String GUID;
    private String title;
    private String description;
    private String author;
    private double price;

    public Book(String title, String description, String author, double price) {
        this.GUID = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public String getGUID() {
        return GUID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
