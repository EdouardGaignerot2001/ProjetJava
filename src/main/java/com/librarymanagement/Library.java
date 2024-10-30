package com.librarymanagement;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public Book getBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getGUID().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public void saveBooks() throws IOException {
        System.out.println("debut save books!");
        File file = new File(
                "Library/src/main/resources/book_catalog.json");

        if (!file.exists()) {
            file.createNewFile();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (books != null && !books.isEmpty()) {

            String json = gson.toJson(new ArrayList<>(books));

            Files.writeString(file.toPath(), json);
            System.out.println("Le catalogue de livres a été sauvegardé dans book_catalog.json !");
        } else {
            System.out.println("La liste des livres est vide ou nulle. Rien à sauvegarder.");
        }
    }

}
