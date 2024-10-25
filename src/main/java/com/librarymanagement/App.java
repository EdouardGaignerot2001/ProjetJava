package com.librarymanagement;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Library library = new Library(); // Créer l'instance de la bibliothèque
        Scanner scanner = new Scanner(System.in);
        User currentUser;

        // Charger les livres depuis le fichier JSON
        JsonBookLoader bookLoader = new JsonBookLoader();
        String jsonFileName = "book_catalog.json"; // Nom de votre fichier JSON dans le dossier resources

        List<Book> books = bookLoader.loadBooksFromJson(jsonFileName);

        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                library.addBook(book);
            }
            System.out.println("Fichier de livres chargé avec succès !");
        } else {
            System.err.println("Aucun livre n'a pu être chargé.");
            return;
        }

        while (true) {
            System.out.println("Entrez votre identifiant utilisateur (ou tapez 'exit' pour quitter) : ");
            String userIdInput = scanner.nextLine();

            if (userIdInput.equals("exit")) {
                System.out.println("Merci et à bientôt !");
                break;
            }

            currentUser = userManager.getUserById(userIdInput);

            if (currentUser != null) {
                System.out.println("Connexion réussie. Bienvenue, " + currentUser.getName() + " !");
                showMenu(scanner, currentUser, library);
                break;
            } else {
                System.out.println("Identifiant non trouvé. Voulez-vous créer un nouvel identifiant ? (oui/non)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("oui")) {
                    System.out.print("Entrez votre nom : ");
                    String userName = scanner.nextLine();
                    userManager.addUser(userIdInput, userName);
                    currentUser = userManager.getUserById(userIdInput);
                    System.out.println("Nouvel identifiant créé. Bienvenue, " + currentUser.getName() + " !");
                    showMenu(scanner, currentUser, library);
                    break;
                } else {
                    System.out.println("Merci et à bientôt !");
                    break;
                }
            }
        }

        scanner.close();
    }

    private static void showMenu(Scanner scanner, User user, Library library) {
        while (true) {
            System.out.println("Menu :");
            System.out.println("1. Louer un livre");
            System.out.println("2. Afficher les livres disponibles");
            System.out.println("3. Voir les détails d'un livre");
            System.out.println("4. Rendre un livre");
            System.out.println("5. Exporter le catalogue");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option : ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    rentBook(scanner, user, library);
                    break;
                case "2":
                    listAvailableBooks(library);
                    break;
                case "3":
                    viewBookDetails(scanner, library);
                    break;
                case "0":
                    System.out.println("Merci d'avoir utilisé l'application. À bientôt !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void listAvailableBooks(Library library) {
        System.out.println("Liste des livres disponibles :");
        for (Book book : library.getBooks()) {
            System.out.println("Titre : " + book.getTitle() + ", ISBN : " + book.getGuidIsbn());
        }
    }

    private static void viewBookDetails(Scanner scanner, Library library) {
        System.out.print("Entrez l'ISBN du livre : ");
        String isbn = scanner.nextLine();
        Book book = library.getBookByIsbn(isbn);

        if (book != null) {
            System.out.println("Détails du livre :");
            System.out.println("ISBN : " + book.getGuidIsbn());
            System.out.println("Titre : " + book.getTitle());
            System.out.println("Auteur : " + book.getAuthor());
            System.out.println("Description : " + book.getDescription());
            System.out.println("Prix : " + book.getPrice());
        } else {
            System.out.println("Livre non trouvé avec l'ISBN fourni.");
        }
    }

    // Méthode pour louer un livre
    private static void rentBook(Scanner scanner, User user, Library library) {
        System.out.print("Entrez l'ISBN du livre que vous souhaitez louer : ");
        String isbn = scanner.nextLine();

        Book book = library.getBookByIsbn(isbn);

        if (book != null) {
            RentalService rentalService = new RentalService(library);
            boolean success = rentalService.rentBook(user, book);

            if (success) {
                System.out.println("Vous avez loué le livre avec succès : " + book.getTitle());
            } else {
                System.out.println(
                        "Échec : Vous ne pouvez pas louer plus de 3 livres ou un exemplaire multiple de ce livre.");
            }
        } else {
            System.out.println("Livre non trouvé avec l'ISBN fourni.");
        }
    }

}
