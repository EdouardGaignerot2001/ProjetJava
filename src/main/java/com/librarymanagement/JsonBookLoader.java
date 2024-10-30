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

    public List<Book> loadBooksFromJson(String fileName) {
        Gson gson = new Gson();
        String filePath;

        try {
            filePath = Paths.get(getClass().getClassLoader().getResource(fileName).toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            System.err.println("Could not find resource: " + fileName);
            return null;
        }

        try (FileReader reader = new FileReader(filePath)) {
            Type bookListType = new TypeToken<List<Book>>() {
            }.getType();
            return gson.fromJson(reader, bookListType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
