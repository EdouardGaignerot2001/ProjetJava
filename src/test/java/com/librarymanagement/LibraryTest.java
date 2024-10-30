package com.librarymanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        library = new Library();
        book1 = new Book("1", "Book One", "Description of Book One", "Author A", 10.99);
        book2 = new Book("2", "Book Two", "Description of Book Two", "Author B", 12.99);
    }

    @Test
    void testAddBook() {
        library.addBook(book1);
        assertEquals(1, library.getBooks().size()); // Vérifie que la bibliothèque contient 1 livre
        assertEquals("Book One", library.getBooks().get(0).getTitle()); // Vérifie que le livre ajouté est le bon
    }

    @Test
    void testGetBookByIsbn() {
        library.addBook(book1);
        library.addBook(book2);
        Book retrievedBook = library.getBookByIsbn("1");
        assertNotNull(retrievedBook); // Vérifie que le livre a été récupéré
        assertEquals("Book One", retrievedBook.getTitle()); // Vérifie que c'est le bon livre

        retrievedBook = library.getBookByIsbn("3"); // ISBN qui n'existe pas
        assertNull(retrievedBook); // Vérifie que le livre n'est pas trouvé
    }

    @Test
    void testSaveBooks() throws IOException {
        library.addBook(book1);
        library.addBook(book2);

        // Sauvegarde les livres et vérifie si le fichier est créé
        library.saveBooks();

        // Vérifie que le fichier a été créé
        File file = new File("Library/src/main/resources/book_catalog.json");
        assertTrue(file.exists());

        // Optionnel : Nettoyez le fichier après le test
        // file.delete();
    }

    @Test
    void testSaveEmptyBooks() throws IOException {
        // Sauvegarde de livres lorsqu'il n'y a pas de livres dans la bibliothèque
        library.saveBooks();

        // Pas de fichier à vérifier ici, mais s'assurer qu'il n'y a pas d'exception lancée
        // Vous pourriez vouloir ajouter des assertions pour vérifier les messages dans la console si nécessaire.
    }
}
