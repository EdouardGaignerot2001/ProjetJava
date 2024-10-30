package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;


public class UserManager {
    private Map<String, User> usersMap;
    // private User userL;

    public UserManager() {
        usersMap = new HashMap<>();

        // this.user =user
        // loadUsersFromJson("src/main/resources/users.json");
    }

    public void loadUsersFromJson(String jsonFileName) {
        JsonUserLoader userLoader = new JsonUserLoader();
        List<User> users = userLoader.loadUsersFromJson(jsonFileName);
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                addUser(user); // Ajouter à la map usersMap
            }
            System.out.println("Fichier de User chargé avec succès !");
        } else {
            System.err.println("Aucun User n'a pu être chargé.");
        }
    }
    

    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(usersMap.get(name.toLowerCase()));
    }

    // Méthode pour ajouter un utilisateur à la map et dans la liste dans User
    private void addUser(User user) {
        usersMap.put(user.getName().toLowerCase(), user);
    }

    public void addUserJson(String name) {
        String id = java.util.UUID.randomUUID().toString(); // Générer un GUID
        User newUser = new User(id, name);
        usersMap.put(name.toLowerCase(), newUser);
        try {
            saveUsers(); // Appel de la méthode avec gestion d'exception
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }
    }

    private void saveUsers() throws IOException {
        File file = new File("/Users/edouardgaignerot/Desktop/Ecole/JUNIA/AP4/JAVA/TPNOTE/Library/src/main/resources/users.json");
    
        // Crée le fichier s'il n'existe pas
        if (!file.exists()) {
            file.createNewFile();
        }
    
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
        // Convertir usersMap en une liste d'utilisateurs pour l'enregistrer sous forme de tableau JSON
        List<User> usersList = new ArrayList<>(usersMap.values());
    
        // Sérialise la liste en JSON
        String json = gson.toJson(usersList);
    
        // Écrit le JSON dans le fichier
        Files.writeString(file.toPath(), json);
    
        System.out.println("Les utilisateurs ont été sauvegardés dans users.json !");
    }
    
}
