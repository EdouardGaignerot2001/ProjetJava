package com.librarymanagement;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.UUID;
import java.io.FileWriter;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import java.io.File;
import com.google.gson.GsonBuilder;

public class LibraryService {
    private Library library;
    private RentalService rentalService;

    public LibraryService(Library library, UserManager userManager) {
        this.library = library;
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
            System.out.println("Livre loué : " + book.isRented());
            System.out.println("Personne : " + book.getRentedBy());
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

        if (success) {

            try {
                library.saveBooks();
                System.out.println("Livre rendu avec succès et catalogue sauvegardé !");
            } catch (IOException e) {
                System.err.println("Erreur lors de la sauvegarde des livres : " + e.getMessage());
            }
        } else {
            System.out.println("Échec du retour du livre.");
        }
    }

    public void createBook(Scanner scanner) {
        String guid = UUID.randomUUID().toString();

        System.out.print("Entrez le titre du livre : ");
        String title = scanner.nextLine();

        System.out.print("Entrez la description du livre : ");
        String description = scanner.nextLine();

        System.out.print("Entrez l'auteur du livre : ");
        String author = scanner.nextLine();

        double price = 0.0;
        boolean validPrice = false;

        while (!validPrice) {
            System.out.print("Entrez le prix du livre : ");
            String priceInput = scanner.nextLine();

            try {
                price = Double.parseDouble(priceInput);
                if (price < 0) {
                    System.out.println("Le prix ne peut pas être négatif. Veuillez réessayer.");
                } else {
                    validPrice = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un chiffre pour le prix.");
            }
        }

        Book newBook = new Book(guid, title, description, author, price);
        library.addBook(newBook);

        try {
            library.saveBooks();
            System.out.println("Le livre a été créé et sauvegardé avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des livres : " + e.getMessage());
        }
    }

    public void exportBooks() {

        String filePath = "Library/src/main/resources/available_books.json";

        List<Book> availableBooks = library.getBooks().stream()
                .filter(book -> !book.isRented())
                .collect(Collectors.toList());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Fichier créé : " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            String json = gson.toJson(availableBooks);
            writer.write(json);
            System.out.println("Le catalogue des livres disponibles a été exporté avec succès vers " + filePath + " !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation du catalogue : " + e.getMessage());
        }
    }
}