package com.librarymanagement;

public class Book {
    private String guidIsbn;
    private String title;
    private String description;
    private String author;
    private double price;

    public Book(String guidIsbn, String title, String description, String author, double price) {
        this.guidIsbn = guidIsbn;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public String getGuidIsbn() {
        return guidIsbn;
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
