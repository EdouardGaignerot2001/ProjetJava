package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users = new HashMap<>();

    public UserManager() {
        loadUsersFromJson("users.json"); // Nom du fichier contenant les utilisateurs
    }

    // Charge les utilisateurs depuis le fichier JSON
    private void loadUsersFromJson(String fileName) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            Type userMapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> userData = gson.fromJson(reader, userMapType);
            for (Map.Entry<String, String> entry : userData.entrySet()) {
                String id = entry.getKey();
                String name = entry.getValue();
                users.put(id, new User(id, name));
            }
        } catch (IOException e) {
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
