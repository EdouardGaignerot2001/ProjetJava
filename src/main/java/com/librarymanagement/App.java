package com.librarymanagement;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library, userManager);
        Scanner scanner = new Scanner(System.in);

        MenuService menuService = new MenuService(userManager, libraryService);
        menuService.loadBooksFromJson("book_catalog.json");
        menuService.loadUsersFromJson("users.json");

        User currentUser = menuService.authenticateUser(scanner);

        if (currentUser != null) {
            menuService.showMenu(scanner, currentUser);
        }

        scanner.close();
    }
}
