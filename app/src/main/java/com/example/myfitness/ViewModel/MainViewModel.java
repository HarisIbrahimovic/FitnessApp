package com.example.myfitness.ViewModel;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myfitness.Model.User;
import com.example.myfitness.Repository.MainRepository;
import com.google.firebase.auth.FirebaseAuth;


public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> loginState = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> acceptLogin = new MutableLiveData<>();
    private MainRepository repository;
    private FirebaseAuth auth;

    public void init(){
        auth = FirebaseAuth.getInstance();
        repository = MainRepository.getInstance();
    }

    public void setLoginState(int state) {
        loginState.setValue(state);
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }
    public LiveData<Integer> getLoginState() {
        return loginState;
    }
    public LiveData<Boolean> getAcceptLogin() {
        return acceptLogin;
    }

    private void signUpUser(String userName, String email, String password, String weight, String height) {
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(userName)||TextUtils.isEmpty(weight)||TextUtils.isEmpty(height)){
            toastMessage.setValue("Please fill in the fields.");
            return;
        }
        if(password.length()<8){
            toastMessage.setValue("Password too short.");
            return;
        }
        if(userName.length()>10){
            toastMessage.setValue("Username too long.");
            return;
        }
        int numWeight = Integer.parseInt(weight);
        int numHeight = Integer.parseInt(height);
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = auth.getCurrentUser().getUid();
                User user = new User(id,numWeight,numHeight,userName,email,password);
                toastMessage.postValue("Welcome.");
                acceptLogin.postValue(true);
                repository.addUser(user);
            }else toastMessage.postValue("Problems");
        });

    }

    private void loginUser(String email, String password) {
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            toastMessage.setValue("Please fill in the fields.");
            return;
        }
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                toastMessage.postValue("Welcome.");
                acceptLogin.postValue(true);
            }else toastMessage.postValue("Login failed.");
        });
    }

    public void fenchData(Application application) {
        repository.fetchData(application);
    }

    public void singUser(String userName, String email, String password, String weight, String height, String buttonText) {
        if(buttonText.equals("Login")){
            loginUser(email,password);
        }else signUpUser(userName,email,password,weight,height);
    }
}
