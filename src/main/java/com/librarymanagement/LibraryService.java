package com.librarymanagement;

import java.util.List;
import java.util.Scanner;

public class LibraryService {
    private Library library;
    private RentalService rentalService;
    private UserManager userManager; // Add UserManager
    public LibraryService(Library library, UserManager userManager) {
        this.library = library;
        this.userManager = userManager;
        this.rentalService = new RentalService(library,userManager);
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
            System.out.println("Titre : " + book.getTitle() + ", ISBN : " + book.getGUID());
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
    
}
