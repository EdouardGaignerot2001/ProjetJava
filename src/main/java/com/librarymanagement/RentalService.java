package com.librarymanagement;

import java.io.IOException;

public class RentalService {
    private Library library;
    private UserManager userManager;

    public RentalService(Library library, UserManager userManager) {
        this.library = library;
        this.userManager = userManager;
    }

    public boolean rentBook(User user, Book book) {

        if (user == null || book == null) {
            System.out.println("L'utilisateur ou le livre est null.");
            return false;
        }

        if (!library.getBooks().contains(book)) {
            System.out.println("Le livre " + book.getTitle() + " n'est pas disponible dans la bibliothèque.");
            return false;
        }

        if (user.hasReachedMaxBooks()) {
            System.out.println("L'utilisateur " + user.getName() + " ne peut plus louer de livres.");
            return false;
        }

        String bookGUID = book.getGUID();
        if (user.alreadyRentedBook(bookGUID)) {
            System.out.println("L'utilisateur " + user.getName() + " a déjà loué le livre " + book.getTitle() + ".");
            return false;
        }

        user.rentBook(bookGUID);
        book.rent(user.getGUID());

        System.out.println("L'utilisateur " + user.getName() + " a loué le livre " + book.getTitle() + ".");

        try {
            library.saveBooks();
            userManager.saveUsers();
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }

        return true;
    }

    public boolean returnBook(User user, String bookGUID) {

        if (user == null) {
            System.out.println("L'utilisateur est null.");
            return false;
        }

        if (!user.alreadyRentedBook(bookGUID)) {
            System.out.println(
                    "L'utilisateur " + user.getName() + " n'a pas loué le livre avec le GUID " + bookGUID + ".");
            return false;
        }

        user.getRentedBooks().remove(bookGUID);

        Book book = library.getBookByIsbn(bookGUID);
        if (book != null) {
            book.returnBook();
            System.out.println(
                    "L'utilisateur " + user.getName() + " a rendu le livre " + book.getTitle() + " avec succès.");
        } else {
            System.out.println("Le livre avec le GUID " + bookGUID + " n'existe pas dans la bibliothèque.");
        }

        try {
            library.saveBooks();
            userManager.saveUsers();

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }

        return true;
    }
}
