package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.fav.response.FavModelResponse;
import com.aqratsa.aeqarat.repositories.FavoriteRepository;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<FavModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void favorite(String realEstateId, String userId, String locale) {
        FavoriteRepository repositoryFavorite = FavoriteRepository.getInstance();
        mResult = repositoryFavorite.favorite(realEstateId, userId, locale);
        mLoading = repositoryFavorite.loading();
        mFailure = repositoryFavorite.failure();
    }

    public LiveData<FavModelResponse> getResult() {
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
