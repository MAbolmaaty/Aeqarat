package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.favorites.FavoritesModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesRepository {

    private static FavoritesRepository instance;
    private Call<FavoritesModelResponse> mCallFavorites;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static FavoritesRepository getInstance(){
        if (instance == null){
            instance = new FavoritesRepository();
        }
        return instance;
    }

    public MutableLiveData<FavoritesModelResponse> favorites(String userId){
        mLoading.setValue(true);
        MutableLiveData<FavoritesModelResponse> favoritesRealEstates = new MutableLiveData<>();
        mCallFavorites = RestClient.getInstance().getApiClient().getFavorites(userId);
        mCallFavorites.enqueue(new Callback<FavoritesModelResponse>() {
            @Override
            public void onResponse(Call<FavoritesModelResponse> call, Response<FavoritesModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    favoritesRealEstates.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavoritesModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return favoritesRealEstates;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
