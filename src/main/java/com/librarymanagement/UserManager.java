package com.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserManager {
    private Set<String> userIds;
    private final String userFileName = "user_ids.json"; // Nom du fichier pour stocker les identifiants d'utilisateur

    public UserManager() {
        userIds = new HashSet<>();
        loadUserIds();
    }

    private void loadUserIds() {
        try (FileReader reader = new FileReader(userFileName)) {
            Type userIdsType = new TypeToken<List<String>>() {}.getType();
            List<String> loadedUserIds = new Gson().fromJson(reader, userIdsType);
            if (loadedUserIds != null) {
                userIds.addAll(loadedUserIds);
            }
        } catch (IOException e) {
            System.err.println("Could not load user IDs: " + e.getMessage());
        }
    }

    public boolean isUserExists(String userId) {
        return userIds.contains(userId);
    }

    public void addUser(String userId) {
        userIds.add(userId);
        saveUserIds();
    }

    private void saveUserIds() {
        try (FileWriter writer = new FileWriter(userFileName)) {
            new Gson().toJson(userIds, writer);
        } catch (IOException e) {
            System.err.println("Could not save user IDs: " + e.getMessage());
        }
    }
}
