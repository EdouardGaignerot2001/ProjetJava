package com.librarymanagement;

public class RentalService {
    private Library library;

    public RentalService(Library library) {
        this.library = library;
    }

    public void rentBook(User user, Book book) {
        if (user.hasReachedMaxBooks()) {
            System.out.println("l'utilisateur " + user.getName() + ", ne peut plus louer de livres");
            return;
        }
        if (user.alreadyRentedBook(book)) {
            System.out.println("\"l'utilisateur " + user.getName() + ", ce livre est deja louer " + book.getTitle());
            return;
        }

        user.rentBook(book);
        System.out.println("l'utilisateur  " + user.getName() +  ", a louer ce livre " + book.getTitle());
    }
}
