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

    // Méthode pour obtenir un livre par son ISBN
    public Book getBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getGUID().equals(isbn)) {
                return book;
            }
        }
        return null; // Retourne null si le livre n'est pas trouvé
    }

    // Méthode pour sauvegarder le catalogue de livres
    // Méthode pour sauvegarder le catalogue de livres
    public void saveBooks() throws IOException {
        System.out.println("debut save books!");
        File file = new File(
                "/Users/edouardgaignerot/Desktop/Ecole/JUNIA/AP4/JAVA/TPNOTE/Library/src/main/resources/book_catalog.json");

        // Crée le fichier s'il n'existe pas
        if (!file.exists()) {
            file.createNewFile();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Vérifie que la liste books contient bien les objets avant la sérialisation
        if (books != null && !books.isEmpty()) {
            // Sérialise la liste des livres en JSON
            String json = gson.toJson(new ArrayList<>(books));

            // Écrit le JSON dans le fichier
            Files.writeString(file.toPath(), json);
            System.out.println("Le catalogue de livres a été sauvegardé dans book_catalog.json !");
        } else {
            System.out.println("La liste des livres est vide ou nulle. Rien à sauvegarder.");
        }
    }

}
