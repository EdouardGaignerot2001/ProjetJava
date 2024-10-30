package com.librarymanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("GUID123", "The Great Gatsby", "A novel by F. Scott Fitzgerald", "F. Scott Fitzgerald", 10.99);
    }

    @Test
    void testGetters() {
        assertEquals("GUID123", book.getGUID());
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("A novel by F. Scott Fitzgerald", book.getDescription());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals(10.99, book.getPrice());
        assertFalse(book.isRented());
        assertNull(book.getRentedBy());
    }

    @Test
    void testRent() {
        book.rent("UserGUID456");
        assertTrue(book.isRented());
        assertEquals("UserGUID456", book.getRentedBy());
    }

    @Test
    void testReturnBook() {
        book.rent("UserGUID456");
        book.returnBook();
        assertFalse(book.isRented());
        assertNull(book.getRentedBy());
    }
}
