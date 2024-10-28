package com.librarymanagement;

public class RentalService {
    private Library library;

    public RentalService(Library library) {
        this.library = library;
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
        if (user.alreadyRentedBook(book)) {
            System.out.println("L'utilisateur " + user.getName() + " a déjà loué le livre " + book.getTitle() + ".");
            return false;
        }

        // Loue le livre à l'utilisateur et le retire de la bibliothèque
        user.rentBook(book);
        library.getBooks().remove(book); // Supprime le livre des livres disponibles

        System.out.println("L'utilisateur " + user.getName() + " a loué le livre " + book.getTitle() + ".");
        return true;
    }
}
