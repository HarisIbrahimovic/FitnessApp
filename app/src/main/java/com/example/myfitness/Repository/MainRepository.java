package com.example.myfitness.Repository;

import com.example.myfitness.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainRepository {
    static MainRepository repository;

    public static MainRepository getInstance(){
        if(repository==null) repository = new MainRepository();
        return repository;
    }

    public void addUser(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getId());
        databaseReference.setValue(user);
    }
}
