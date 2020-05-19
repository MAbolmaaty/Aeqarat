package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.info_update.UpdateInfoModelResponse;
import com.aqratsa.aeqarat.repositories.InfoUpdateRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

// Leaking
public class InfoUpdateViewModel extends ViewModel {

    private MutableLiveData<UpdateInfoModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mInfoUpdated = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure;

    public void updateInfo(RequestBody userId,
                           RequestBody username,
                           RequestBody email,
                           RequestBody phoneNumber,
                           RequestBody phoneCode,
                           RequestBody birthday,
                           RequestBody address,
                           RequestBody locale,
                           MultipartBody.Part profilePhoto){

        InfoUpdateRepository repositoryUpdateInfo = InfoUpdateRepository.getInstance();
        mResult = repositoryUpdateInfo.updateInfo(userId, username, email, phoneNumber,
                phoneCode, birthday, address, locale, profilePhoto);
        mLoading = repositoryUpdateInfo.loading();
        mFailure = repositoryUpdateInfo.failure();
    }

    public LiveData<UpdateInfoModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading (){
        return mLoading;
    }

    public void infoUpdated(boolean updated){
        mInfoUpdated.setValue(updated);
    }

    public LiveData<Boolean> isInfoUpdated(){
        return mInfoUpdated;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
