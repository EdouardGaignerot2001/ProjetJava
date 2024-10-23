package com.librarymanagement;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Library library = new Library();
        User user = new User("123", "John Doe");

        JsonBookLoader bookLoader = new JsonBookLoader();
        String jsonFileName = "book_catalog.json"; // Nom de votre fichier JSON dans le dossier resources

        // Charger les livres depuis le fichier JSON
        List<Book> books = bookLoader.loadBooksFromJson(jsonFileName);

        // Vérifier si les livres ont été chargés avec succès
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                library.addBook(book); // Ajouter chaque livre à la bibliothèque
                
            }
            System.out.println("fichier de livre charger");
        } else {
            System.err.println("Aucun livre n'a pu être chargé.");
            return; // Arrêter l'exécution si aucun livre n'est chargé
        }

        RentalService rentalService = new RentalService(library);

        // Exemples de location de livres
        if (books.size() >= 2) { // S'assurer qu'il y a assez de livres
            rentalService.rentBook(user, books.get(0)); // Loue le premier livre
            rentalService.rentBook(user, books.get(1)); // Loue le deuxième livre
        }

        // Vérification de la location d'un livre déjà loué
        rentalService.rentBook(user, books.get(0)); // Essaie de louer à nouveau le premier livre

        // Impression des ISBN pour vérification
        System.out.println("Book 1 ISBN: " + books.get(0).getGuidIsbn());
        System.out.println("Book 2 ISBN: " + books.get(1).getGuidIsbn());
    }
}
