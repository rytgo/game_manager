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

    public boolean createAccount(String username, String password){
        if (users.containsKey(username)){
            System.out.println("Username already exists!");
            return false;
        }
        users.put(username, password);
        saveUser(username, password);
        return true;
    }

    /*
     * The reason there is a separate method for saving to file is for the purpose of possible encryption
     */
    private void saveUser(String username, String password){
        try{
            //Explaining this line
            //First parameter must be type Path, second parameter converts a String into byte array byte[],
            //Third parameter tells Files.write() to add data to the end of the existing file
            Files.write(Paths.get(fileName), (username + ":" + password + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.APPEND);
            System.out.println("Saved!");
        } catch (IOException e){
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

}
