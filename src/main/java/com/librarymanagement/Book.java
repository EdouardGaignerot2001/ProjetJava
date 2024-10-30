package com.librarymanagement;

public class Book {
    private String GUID;
    private String title;
    private String description;
    private String author;
    private double price;
    private boolean isRented;
    private String rentedBy;

    public Book(String GUID, String title, String description, String author, double price) {
        this.GUID = GUID;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.isRented = false;
        this.rentedBy = null;
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

    public boolean isRented() {
        return isRented;
    }

    public String getRentedBy() {
        return rentedBy;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public void rent(String userGUID) {
        this.isRented = true;
        this.rentedBy = userGUID;
    }

    public void returnBook() {
        this.isRented = false;
        this.rentedBy = null;
    }
}
