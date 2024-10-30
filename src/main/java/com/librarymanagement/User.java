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
    private List<String> rentedBooks; // Changement ici pour stocker les GUIDs

    public User(String id, String name) {
        this.GUID = UUID.randomUUID().toString();
        this.name = name;
        this.rentedBooks = new ArrayList<>(); // Initialise rentedBooks
    }

    public String getGUID() {
        return GUID;
    }

    public String getName() {
        return name;
    }

    public List<String> getRentedBooks() {
        return rentedBooks; // Renvoie la liste des GUIDs des livres loués
    }

    public void rentBook(String bookGUID) {
        rentedBooks.add(bookGUID); // Ajoute le GUID du livre loué
    }

    public boolean hasReachedMaxBooks() {
        return rentedBooks.size() >= 3; // Limite de 3 livres
    }

    public boolean alreadyRentedBook(String bookGUID) {
        return rentedBooks.contains(bookGUID); // Vérifie si le livre est déjà loué
    }

    public void saveToJson(String jsonFileName, List<User> users) {
        // Logique pour sauvegarder les informations de l'utilisateur dans le fichier
        // JSON
        // On va utiliser Gson pour écrire la liste d'utilisateurs
        try (Writer writer = new FileWriter(jsonFileName)) {
            Gson gson = new Gson();
            gson.toJson(users, writer); // Écrit la liste d'utilisateurs dans le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
