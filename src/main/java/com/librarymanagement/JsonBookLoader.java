package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class JsonBookLoader {

    // Charge la liste des livres depuis le fichier JSON
    public List<Book> loadBooksFromJson(String fileName) {
        Gson gson = new Gson();
        String filePath;

        // Utiliser getResource pour trouver le fichier dans le dossier resources
        try {
            // Charger la ressource
            filePath = Paths.get(getClass().getClassLoader().getResource(fileName).toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            System.err.println("Could not find resource: " + fileName);
            return null; // Si le fichier n'est pas trouvé
        }

        try (FileReader reader = new FileReader(filePath)) {
            // Lire le tableau de livres directement
            Type bookListType = new TypeToken<List<Book>>() {}.getType();
            return gson.fromJson(reader, bookListType); // Changement ici
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
