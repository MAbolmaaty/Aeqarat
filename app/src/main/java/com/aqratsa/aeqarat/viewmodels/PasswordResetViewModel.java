package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.password_reset.response.ResetPasswordModelResponse;
import com.aqratsa.aeqarat.repositories.PasswordResetRepository;

public class PasswordResetViewModel extends ViewModel {

    private MutableLiveData<ResetPasswordModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void resetPassword(String email,
                              String newPassword,
                              String confirmPassword,
                              String locale){

        PasswordResetRepository repositoryResetPassword = PasswordResetRepository.getInstance();
        mResult = repositoryResetPassword.resetPassword(email, newPassword, confirmPassword,
                locale);
        mLoading = repositoryResetPassword.loading();
        mFailure = repositoryResetPassword.failure();
    }

    public LiveData<ResetPasswordModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
