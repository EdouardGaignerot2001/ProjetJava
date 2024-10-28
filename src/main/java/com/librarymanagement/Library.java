package com.librarymanagement;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    // Méthode pour obtenir un livre par son ISBN
    public Book getBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getGUID().equals(isbn)) {
                return book;
            }
        }
        return null; // Retourne null si le livre n'est pas trouvé
    }
}
