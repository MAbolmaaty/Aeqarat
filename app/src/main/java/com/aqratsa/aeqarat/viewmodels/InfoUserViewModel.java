package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.info_user.UserInfoModelResponse;
import com.aqratsa.aeqarat.repositories.InfoUserRepository;

// Leaking
public class InfoUserViewModel extends ViewModel {

    private MutableLiveData<UserInfoModelResponse> mUserInfo;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mDeleting = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure;

    public void userInfo(String userId){
        InfoUserRepository repositoryUserInfo = InfoUserRepository.getInstance();
        mUserInfo = repositoryUserInfo.userInfo(userId);
        mLoading = repositoryUserInfo.loading();
        mFailure = repositoryUserInfo.failure();
    }

    public LiveData<UserInfoModelResponse> getUserInfo(){
        return mUserInfo;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public void deleting (boolean deleting){
        mDeleting.setValue(deleting);
    }

    public LiveData<Boolean> isDeleting(){
        return mDeleting;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
