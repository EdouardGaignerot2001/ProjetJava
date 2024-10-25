package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users = new HashMap<>();

    public UserManager() {
        loadUsersFromJson("/users.json"); // Charge le fichier JSON depuis les ressources
    }

    // Charge les utilisateurs depuis le fichier JSON dans les ressources
    private void loadUsersFromJson(String fileName) {
        Gson gson = new Gson();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(fileName))) {
            Type userMapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> userData = gson.fromJson(reader, userMapType);
            for (Map.Entry<String, String> entry : userData.entrySet()) {
                String id = entry.getKey();
                String name = entry.getValue();
                users.put(id, new User(id, name));
            }
            System.out.println("Fichier des utilisateurs chargé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des utilisateurs : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public User getUserById(String id) {
        return users.get(id);
    }

    public boolean isUserExists(String id) {
        return users.containsKey(id);
    }

    public void addUser(String id, String name) {
        users.put(id, new User(id, name));
    }
}
