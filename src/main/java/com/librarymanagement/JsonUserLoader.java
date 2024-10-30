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

        try {
            filePath = Paths.get(getClass().getClassLoader().getResource(fileName).toURI()).toString();
        } catch (URISyntaxException | NullPointerException e) {
            System.err.println("Ressource introuvable : " + fileName);
            return null;
        }

        try (FileReader reader = new FileReader(filePath)) {
            Type userListType = new TypeToken<List<User>>() {
            }.getType();
            List<User> users = gson.fromJson(reader, userListType);
            return users;
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            return null;
        }
    }

}
