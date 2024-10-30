package com.librarymanagement;

import java.io.IOException;

public class RentalService {
    private Library library;
    private UserManager userManager; // Ajoutez une référence à UserManager

    public RentalService(Library library, UserManager userManager) {
        this.library = library;
        this.userManager = userManager; // Initialisez le UserManager
    }

    public boolean rentBook(User user, Book book) {
        // Vérifie si le livre est disponible dans la bibliothèque
        if (!library.getBooks().contains(book)) {
            System.out.println("Le livre " + book.getTitle() + " n'est pas disponible dans la bibliothèque.");
            return false;
        }

        // Vérifie si l'utilisateur a atteint sa limite de livres loués
        if (user.hasReachedMaxBooks()) {
            System.out.println("L'utilisateur " + user.getName() + " ne peut plus louer de livres.");
            return false;
        }

        // Vérifie si l'utilisateur a déjà loué ce livre
        String bookGUID = book.getGUID(); // Supposons que vous ayez une méthode getGUID() dans Book
        if (user.alreadyRentedBook(bookGUID)) {
            System.out.println("L'utilisateur " + user.getName() + " a déjà loué le livre " + book.getTitle() + ".");
            return false;
        }

        // Loue le livre à l'utilisateur et le retire de la bibliothèque
        user.rentBook(bookGUID); // Ajoute le GUID du livre à la liste des livres loués
        library.getBooks().remove(book); // Supprime le livre des livres disponibles

        System.out.println("L'utilisateur " + user.getName() + " a loué le livre " + book.getTitle() + ".");

        // Sauvegarde l'utilisateur après avoir modifié les livres loués
        try {
            userManager.saveUsers(); // Utilisez l'instance de UserManager ici
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }

        return true;
    }

    public boolean returnBook(User user, String bookGUID) {
        // Vérifie si l'utilisateur a déjà loué ce livre
        if (!user.alreadyRentedBook(bookGUID)) {
            System.out.println(
                    "L'utilisateur " + user.getName() + " n'a pas loué le livre avec le GUID " + bookGUID + ".");
            return false;
        }

        // Supprime le GUID du livre de la liste des livres loués de l'utilisateur
        user.getRentedBooks().remove(bookGUID);

        // Ajoute le livre à la bibliothèque
        Book book = library.getBookByIsbn(bookGUID);
        if (book != null) {
            library.addBook(book); // Ajoute le livre à la bibliothèque
            System.out.println(
                    "L'utilisateur " + user.getName() + " a rendu le livre " + book.getTitle() + " avec succès.");
        } else {
            System.out.println("Le livre avec le GUID " + bookGUID + " n'existe pas dans la bibliothèque.");
        }

        // Sauvegarde l'utilisateur après avoir modifié les livres loués
        try {
            userManager.saveUsers(); // Assurez-vous que userManager est accessible ici
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }

        return true;
    }

}
