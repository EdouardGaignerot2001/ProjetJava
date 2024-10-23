package com.librarymanagement;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        User user = new User("123", "John Doe");

        // Create books without specifying ISBN
        Book book1 = new Book("Effective Java", "A guide to Java programming", "Joshua Bloch", 45.99);
        Book book2 = new Book("Clean Code", "A Handbook of Agile Software Craftsmanship", "Robert C. Martin", 39.99);

        library.addBook(book1);
        library.addBook(book2);

        RentalService rentalService = new RentalService(library);

        rentalService.rentBook(user, book1);
        rentalService.rentBook(user, book2);

        // Try renting the same book again or more than 3 books to see the restrictions
        rentalService.rentBook(user, book1); // Should show error

        // Print out the books and their auto-generated ISBNs
        System.out.println("Book 1 ISBN: " + book1.getGuidIsbn());
        System.out.println("Book 2 ISBN: " + book2.getGuidIsbn());
    }
}
