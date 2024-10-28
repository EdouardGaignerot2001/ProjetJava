package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class UserManager {
    private Map<String, User> usersMap;
    // private User userL;

    public UserManager() {
        usersMap = new HashMap<>();
       
       // this.user =user
        //loadUsersFromJson("src/main/resources/users.json");
    }

    public void loadUsersFromJson(String jsonFileName) {
        JsonUserLoader userLoader = new JsonUserLoader();
        List<User> users = userLoader.loadUsersFromJson(jsonFileName);
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                addUser(user);
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
        saveUserToJson(newUser);
    }

    private void saveUserToJson(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("src/main/resources/users.json", true)) {
            writer.write(gson.toJson(user));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
        }
    }
}
