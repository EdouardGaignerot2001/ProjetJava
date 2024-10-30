package com.librarymanagement;

import java.io.IOException;

public class RentalService {
    private Library library;
    private UserManager userManager; // Référence à UserManager

    public RentalService(Library library, UserManager userManager) {
        this.library = library; // Initialisation de la bibliothèque
        this.userManager = userManager; // Initialisation de UserManager
    }

    public boolean rentBook(User user, Book book) {
        // Vérifiez que l'utilisateur et le livre ne sont pas null
        if (user == null || book == null) {
            System.out.println("L'utilisateur ou le livre est null.");
            return false;
        }
    
        // Vérifiez si le livre est disponible dans la bibliothèque
        if (!library.getBooks().contains(book)) {
            System.out.println("Le livre " + book.getTitle() + " n'est pas disponible dans la bibliothèque.");
            return false;
        }
    
        // Vérifiez si l'utilisateur a atteint sa limite de livres loués
        if (user.hasReachedMaxBooks()) {
            System.out.println("L'utilisateur " + user.getName() + " ne peut plus louer de livres.");
            return false;
        }
    
        // Vérifiez si l'utilisateur a déjà loué ce livre
        String bookGUID = book.getGUID();
        if (user.alreadyRentedBook(bookGUID)) {
            System.out.println("L'utilisateur " + user.getName() + " a déjà loué le livre " + book.getTitle() + ".");
            return false;
        }
    
        // Louez le livre à l'utilisateur
        user.rentBook(bookGUID); // Ajoute le GUID du livre à la liste des livres loués
        book.rent(user.getGUID()); // Marque le livre comme loué et enregistre l'utilisateur
    
        // Retirez le livre de la bibliothèque
        //library.getBooks().remove(book); // Supprime le livre des livres disponibles
    
        System.out.println("L'utilisateur " + user.getName() + " a loué le livre " + book.getTitle() + ".");
    
        // Sauvegardez les utilisateurs après avoir modifié les livres loués
        try {
            library.saveBooks();
            userManager.saveUsers(); // Utilisez l'instance pour appeler saveUsers
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }
    
        return true; // Retourne true pour indiquer que la location a réussi
    }
    

    public boolean returnBook(User user, String bookGUID) {
        // Vérifiez que l'utilisateur n'est pas null
        if (user == null) {
            System.out.println("L'utilisateur est null.");
            return false;
        }

        // Vérifiez si l'utilisateur a déjà loué ce livre
        if (!user.alreadyRentedBook(bookGUID)) {
            System.out.println("L'utilisateur " + user.getName() + " n'a pas loué le livre avec le GUID " + bookGUID + ".");
            return false;
        }

        // Supprimez le GUID du livre de la liste des livres loués de l'utilisateur
        user.getRentedBooks().remove(bookGUID);

        // Récupérez le livre par son GUID
        Book book = library.getBookByIsbn(bookGUID);
        if (book != null) {
            book.returnBook(); // Réinitialisez l'état du livre
            library.addBook(book); // Ajoutez le livre à la bibliothèque
            System.out.println("L'utilisateur " + user.getName() + " a rendu le livre " + book.getTitle() + " avec succès.");
        } else {
            System.out.println("Le livre avec le GUID " + bookGUID + " n'existe pas dans la bibliothèque.");
        }

        // Sauvegardez les utilisateurs après avoir modifié les livres loués
        try {
            library.saveBooks();
            userManager.saveUsers(); // Utilisez l'instance pour appeler saveUsers
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }

        return true; // Retourne true pour indiquer que le retour a réussi
    }
}
