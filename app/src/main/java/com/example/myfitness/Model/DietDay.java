package com.example.myfitness.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DietDay {
    @PrimaryKey
    private String id;
    private int protein;
    private int carbs;
    private int fats;
    private int calories;


    DietDay(){
    }

    public DietDay(String id,int protein, int carbs, int fats, int calories) {
        this.id = id;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.calories = calories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
