package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class UserManager {
    private Map<String, User> usersMap;

    public UserManager() {
        usersMap = new HashMap<>();

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

    private void addUser(User user) {
        usersMap.put(user.getName().toLowerCase(), user);
    }

    public void addUserJson(String name) {
        String id = java.util.UUID.randomUUID().toString();
        User newUser = new User(id, name);
        usersMap.put(name.toLowerCase(), newUser);
        try {
            saveUsers();
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }
    }

    public void saveUsers() throws IOException {
        File file = new File(
                "Library/src/main/resources/users.json");

        if (!file.exists()) {
            file.createNewFile();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<User> usersList = new ArrayList<>(usersMap.values());

        String json = gson.toJson(usersList);

        Files.writeString(file.toPath(), json);

        System.out.println("Les utilisateurs ont été sauvegardés dans users.json !");
    }

}
