package com.example.myfitness.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    private String id;
    private int weight;
    private int height;
    private String username;
    private String email;
    private String password;

    public String getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(String id, int weight, int height, String username, String email, String password) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

}
