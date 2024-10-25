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

    public User authenticateUser(Scanner scanner) {
        while (true) {
            System.out.print("Entrez votre identifiant utilisateur (ou tapez 'exit' pour quitter) : ");
            String userIdInput = scanner.nextLine();
            
            if ("exit".equalsIgnoreCase(userIdInput)) {
                System.out.println("Merci et à bientôt !");
                return null;
            }

            User user = userManager.getUserById(userIdInput);
            if (user != null) {
                System.out.println("Connexion réussie. Bienvenue, " + user.getName() + " !");
                return user;
            } else {
                System.out.print("Identifiant non trouvé. Voulez-vous créer un nouvel identifiant ? (oui/non): ");
                if ("oui".equalsIgnoreCase(scanner.nextLine())) {
                    System.out.print("Entrez votre nom : ");
                    String userName = scanner.nextLine();
                    userManager.addUser(userIdInput, userName);
                    System.out.println("Nouvel identifiant créé. Bienvenue, " + userName + " !");
                    return userManager.getUserById(userIdInput);
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
                case "0":
                    System.out.println("Merci d'avoir utilisé l'application. À bientôt !");
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
}
