package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.repositories.LoginRepository;

// Leaking
public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginModelResponse> user = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoggedIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    private MutableLiveData<String> mConfirmEmailFor = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLogin = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLogout = new MutableLiveData<>();

    public void login(String email, String password, String locale,
                      String fcmToken, String deviceOS){
        LoginRepository repositoryLogin = LoginRepository.getInstance();
        user = repositoryLogin.getUser(email, password, locale, fcmToken, deviceOS);
        mLoading = repositoryLogin.loading();
        mFailure = repositoryLogin.failure();
    }

    public LiveData<LoginModelResponse> getUser(){
        return user;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public void confirmEmailFor(String emailFor){
        mConfirmEmailFor.setValue(emailFor);
    }

    public LiveData<String> confirmFor(){
        return mConfirmEmailFor;
    }

    public void loggedIn(boolean isLoggedIn){
        mLoggedIn.setValue(isLoggedIn);
    }

    public LiveData<Boolean> isLoggedIn(){
        return mLoggedIn;
    }

    public void loginOpened(boolean opened){
        mLogin.setValue(opened);
    }

    public LiveData<Boolean> isLoginOpened(){
        return mLogin;
    }

    public LiveData<Boolean> failure(){return mFailure;}

    public void logout(boolean b){
        mLogout.setValue(b);
    }

    public LiveData<Boolean> isLoggedout(){
        return mLogout;
    }
}
