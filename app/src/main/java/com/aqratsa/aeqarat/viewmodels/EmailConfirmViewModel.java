package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.email_confirm.response.ConfirmEmailModelResponse;
import com.aqratsa.aeqarat.repositories.EmailConfirmRepository;

public class EmailConfirmViewModel extends ViewModel {

    private MutableLiveData<ConfirmEmailModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure;

    public void confirmEmail(String email, String locale){
        EmailConfirmRepository repositoryConfirmEmail = EmailConfirmRepository.getInstance();
        mResult = repositoryConfirmEmail.confirmEmail(email, locale);
        mLoading = repositoryConfirmEmail.loading();
        mFailure = repositoryConfirmEmail.failure();
    }

    public void email(String email){
        mEmail.setValue(email);
    }

    public LiveData<ConfirmEmailModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<String> getEmail(){
        return mEmail;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
