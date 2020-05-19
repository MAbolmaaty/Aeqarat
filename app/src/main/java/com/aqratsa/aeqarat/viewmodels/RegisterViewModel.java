package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.register.RegisterModelResponse;
import com.aqratsa.aeqarat.repositories.RegisterRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void register(RequestBody username,
                         RequestBody email, RequestBody password, RequestBody countryCode,
                         RequestBody phoneNumber, RequestBody locale, MultipartBody.Part userImage,
                         RequestBody fcmToken, RequestBody device){

        RegisterRepository repositoryRegister = RegisterRepository.getInstance();
        mResult = repositoryRegister.register(username,
                email, password, countryCode, phoneNumber, locale, userImage, fcmToken, device);
        mLoading = repositoryRegister.loading();
        mFailure = repositoryRegister.failure();

    }

    public LiveData<RegisterModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
