package com.librarymanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        // Créez un nouvel utilisateur avant chaque test
        user = new User(UUID.randomUUID().toString(), "John Doe");
    }

    @Test
    void testGetGUID() {
        // Vérifiez que le GUID est généré et n'est pas nul
        assertNotNull(user.getGUID());
    }

    @Test
    void testGetName() {
        // Vérifiez que le nom de l'utilisateur est correct
        assertEquals("John Doe", user.getName());
    }

    @Test
    void testRentBook() {
        // Louez un livre et vérifiez qu'il est dans la liste des livres loués
        user.rentBook("book1");
        assertTrue(user.getRentedBooks().contains("book1"));
    }

    @Test
    void testRentBookAlreadyRented() {
        // Louez un livre deux fois et vérifiez que la taille de la liste est toujours 1
        user.rentBook("book1");
        user.rentBook("book1"); // Essayer de louer le même livre
        assertEquals(1, user.getRentedBooks().size());
    }

    @Test
    void testHasReachedMaxBooks() {
        // Louez trois livres, vérifiez que la limite est atteinte
        user.rentBook("book1");
        user.rentBook("book2");
        user.rentBook("book3");
        assertTrue(user.hasReachedMaxBooks());

        // Essayer de louer un quatrième livre, cela ne devrait pas être ajouté
        user.rentBook("book4");
        assertEquals(3, user.getRentedBooks().size());
    }

    @Test
    void testAlreadyRentedBook() {
        // Louez un livre et vérifiez que la méthode renvoie true
        user.rentBook("book1");
        assertTrue(user.alreadyRentedBook("book1"));
        assertFalse(user.alreadyRentedBook("book2"));
    }

    @Test
    void testSaveToJson() {
        // Créez une liste d'utilisateurs et sauvegardez-la en JSON
        List<User> users = new ArrayList<>();
        users.add(user);
        user.saveToJson("users.json", users);

        // Vous pouvez ajouter des assertions ici pour vérifier si le fichier a été créé correctement,
        // mais cela nécessiterait de vérifier le système de fichiers ou le contenu du fichier.
        // Ce test est un peu limité en termes de vérification sans lire le fichier JSON.
    }
}
