package com.librarymanagement;

import java.util.Scanner;

public class MenuService {
    private UserManager userManager;
    private LibraryService libraryService;

    public MenuService(UserManager userManager, LibraryService libraryService) {
        this.userManager = userManager;
        this.libraryService = libraryService;
    }

    public void loadBooksFromJson(String jsonFileName) {
        libraryService.loadBooksFromJson(jsonFileName);
    }

    public void loadUsersFromJson(String jsonFileName) {
        userManager.loadUsersFromJson(jsonFileName);
    }

    public User authenticateUser(Scanner scanner) {
        while (true) {
            System.out.print("Entrez votre nom d'utilisateur (ou tapez 'exit' pour quitter) : ");
            String userNameInput = scanner.nextLine();

            if ("exit".equalsIgnoreCase(userNameInput)) {
                System.out.println("Merci et à bientôt !");
                return null;
            }

            // Chercher l'utilisateur par nom
            User user = userManager.getUserByName(userNameInput).orElse(null);
            if (user != null) {
                System.out.println("Connexion réussie. Bienvenue, " + user.getName() + " !");
                return user;
            } else {
                System.out.print("Nom d'utilisateur non trouvé. Voulez-vous créer un nouvel utilisateur ? (oui/non): ");
                if ("oui".equalsIgnoreCase(scanner.nextLine())) {
                    userManager.addUserJson(userNameInput); // Crée et enregistre un nouvel utilisateur
                    System.out.println("Nouvel utilisateur créé. Bienvenue, " + userNameInput + " !");
                    // Récupère l'utilisateur nouvellement ajouté pour le retourner
                    return userManager.getUserByName(userNameInput).orElse(null);
                } else {
                    System.out.println("Merci et à bientôt !");
                    return null;
                }
            }
        }
    }

    public void showMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Menu :");
            System.out.println("1. Louer un livre");
            System.out.println("2. Afficher les livres disponibles");
            System.out.println("3. Voir les détails d'un livre");
            System.out.println("4. Rendre un livre");
            System.out.println("5. Exporter le catalogue");
            System.out.println("6. crer un livre");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option : ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    libraryService.rentBook(scanner, user);
                    break;
                case "2":
                    libraryService.listAvailableBooks();
                    break;
                case "3":
                    libraryService.viewBookDetails(scanner);
                    break;
                case "4":
                    libraryService.returnBook(scanner, user);
                    break;
                case "0":
                    System.out.println("Merci d'avoir utilisé l'application. À bientôt !");
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
}
