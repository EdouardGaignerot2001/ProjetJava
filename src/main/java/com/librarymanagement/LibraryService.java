package com.librarymanagement;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.UUID;

public class LibraryService {
    private Library library;
    private RentalService rentalService;
    private UserManager userManager; // Add UserManager

    public LibraryService(Library library, UserManager userManager) {
        this.library = library;
        this.userManager = userManager;
        this.rentalService = new RentalService(library, userManager);
    }

    public void loadBooksFromJson(String jsonFileName) {
        JsonBookLoader bookLoader = new JsonBookLoader();
        List<Book> books = bookLoader.loadBooksFromJson(jsonFileName);
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                library.addBook(book);
            }
            System.out.println("Fichier de livres chargé avec succès !");
        } else {
            System.err.println("Aucun livre n'a pu être chargé.");
        }
    }

    public void listAvailableBooks() {
        System.out.println("Liste des livres disponibles :");
        for (Book book : library.getBooks()) {
            System.out.println("Titre : " + book.getTitle() + ", ISBN : " + book.getGUID() + ", Price : "
                    + book.getPrice() + ", Livre loué: " + book.isRented() + ", Personne : " + book.getRentedBy());
        }
    }

    public void viewBookDetails(Scanner scanner) {
        System.out.print("Entrez l'ISBN du livre : ");
        String isbn = scanner.nextLine();
        Book book = library.getBookByIsbn(isbn);

        if (book != null) {
            System.out.println("Détails du livre :");
            System.out.println("ISBN : " + book.getGUID());
            System.out.println("Titre : " + book.getTitle());
            System.out.println("Auteur : " + book.getAuthor());
            System.out.println("Description : " + book.getDescription());
            System.out.println("Prix : " + book.getPrice());
        } else {
            System.out.println("Livre non trouvé avec l'ISBN fourni.");
        }
    }

    public void rentBook(Scanner scanner, User user) {
        System.out.print("Entrez l'ISBN du livre que vous souhaitez louer : ");
        String isbn = scanner.nextLine();
        Book book = library.getBookByIsbn(isbn);

        if (book != null) {
            boolean success = rentalService.rentBook(user, book);
            System.out.println(success ? "Livre loué avec succès !" : "Location échouée.");
        } else {
            System.out.println("Livre non trouvé avec l'ISBN fourni.");
        }
    }

    public void returnBook(Scanner scanner, User user) {
        System.out.print("Entrez le GUID du livre que vous souhaitez rendre : ");
        String bookGUID = scanner.nextLine();
        boolean success = rentalService.returnBook(user, bookGUID);
        System.out.println(success ? "Livre rendu avec succès !" : "Échec du retour du livre.");
    }

    public void createBook(Scanner scanner) {
        String guid = UUID.randomUUID().toString();
    
        System.out.print("Entrez le titre du livre : ");
        String title = scanner.nextLine();
    
        System.out.print("Entrez la description du livre : ");
        String description = scanner.nextLine();
    
        System.out.print("Entrez l'auteur du livre : ");
        String author = scanner.nextLine();
    
        double price = 0.0; // Initialisation de la variable price
        boolean validPrice = false; // Indicateur de validation
    
        // Validation de l'entrée pour le prix
        while (!validPrice) {
            System.out.print("Entrez le prix du livre : ");
            String priceInput = scanner.nextLine(); // Lire l'entrée comme une chaîne
    
            try {
                price = Double.parseDouble(priceInput); // Convertir la chaîne en double
                if (price < 0) {
                    System.out.println("Le prix ne peut pas être négatif. Veuillez réessayer.");
                } else {
                    validPrice = true; // Prix valide, sortir de la boucle
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un chiffre pour le prix.");
            }
        }
    
        Book newBook = new Book(guid, title, description, author, price);
        library.addBook(newBook);
    
        // Sauvegardez les livres après avoir ajouté un nouveau livre
        try {
            library.saveBooks();
            System.out.println("Le livre a été créé et sauvegardé avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des livres : " + e.getMessage());
        }
    }
}    