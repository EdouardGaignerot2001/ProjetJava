package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class JsonUserLoader {

    // Charge la liste des utilisateurs depuis le fichier JSON
    public List<User> loadUsersFromJson(String fileName) {
        Gson gson = new Gson();
        String filePath;

        // Utiliser getResource pour trouver le fichier dans le dossier resources
        try {
            filePath = Paths.get(getClass().getClassLoader().getResource(fileName).toURI()).toString();
           // System.out.println("Chemin du fichier JSON : " + filePath); // Debug : Vérifie le chemin
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            System.err.println("Ressource introuvable : " + fileName);
            return null;
        }

        try (FileReader reader = new FileReader(filePath)) {
           // System.out.println("Lecture du fichier JSON..."); // Debug : confirme que le fichier est lu

            // Lire le contenu brut pour le débogage
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
           // System.out.println("Contenu JSON brut : " + jsonObject.toString()); // Debug : Affiche le contenu JSON

            // Extraire le tableau d’utilisateurs
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(jsonObject.get("users"), userListType);

            if (users != null) {
               // System.out.println("Nombre d'utilisateurs chargés : " + users.size()); // Debug : Vérifie le nombre d'utilisateurs
            } else {
                System.err.println("Erreur : Aucun utilisateur trouvé dans le fichier JSON.");
            }

            return users;
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
