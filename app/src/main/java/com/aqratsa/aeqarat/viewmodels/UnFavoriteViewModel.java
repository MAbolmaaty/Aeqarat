package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.unfavorite.response.UnFavoriteModelResponse;
import com.aqratsa.aeqarat.repositories.UnFavoriteRepository;

public class UnFavoriteViewModel extends ViewModel {

    private MutableLiveData<UnFavoriteModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void unFavorite(String realEstateId, String userId, String locale){
        UnFavoriteRepository repositoryUnFavorite = UnFavoriteRepository.getInstance();
        mResult = repositoryUnFavorite.unFavorite(realEstateId, userId, locale);
        mLoading = repositoryUnFavorite.loading();
        mFailure = repositoryUnFavorite.failure();
    }

    public LiveData<UnFavoriteModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
