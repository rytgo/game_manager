package com.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class LoginManager {
    private String fileName;
    private HashMap<String,String> users;

    public LoginManager(String fileName){
        this.fileName = fileName;
        this.users = new HashMap<>();
        loadUsers();
    }

    private void loadUsers(){
        try{
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line: lines){
                String[] parts = line.split(":");
                if (parts.length == 2){
                    users.put(parts[0], parts[1]); // Store username and password in the map
                    System.out.println("Username: " + parts[0] + ", Password: " + parts[1]);
                }
            }
        } catch (IOException e){
            System.out.println("Error loading users: " + e.getMessage() + " (The file cannot be found!)" );
        }
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }



}
