package com.librarymanagement;

public class RentalService {
    private Library library;

    public RentalService(Library library) {
        this.library = library;
    }

    public boolean rentBook(User user, Book book) {
        if (!library.getBooks().contains(book)) {
            System.out.println("Le livre " + book.getTitle() + " n'est pas disponible dans la bibliothèque.");
            return false;
        }

        if (user.hasReachedMaxBooks()) {
            System.out.println("L'utilisateur " + user.getName() + " ne peut plus louer de livres.");
            return false;
        }

        if (user.alreadyRentedBook(book)) {
            System.out.println("L'utilisateur " + user.getName() + " a déjà loué le livre " + book.getTitle() + ".");
            return false;
        }

        user.rentBook(book);
        System.out.println("L'utilisateur " + user.getName() + " a loué le livre " + book.getTitle() + ".");
        return true;
    }
}
