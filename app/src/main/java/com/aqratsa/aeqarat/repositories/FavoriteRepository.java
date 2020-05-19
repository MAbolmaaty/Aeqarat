package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.fav.request.FavModelRequest;
import com.aqratsa.aeqarat.models.fav.response.FavModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteRepository {

    private static FavoriteRepository instance;
    private Call<FavModelResponse> mCallFavorite;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static FavoriteRepository getInstance(){
        if (instance == null){
            instance = new FavoriteRepository();
        }
        return instance;
    }

    public MutableLiveData<FavModelResponse> favorite(String realEstateId, String userId, String locale){
        mLoading.setValue(true);
        MutableLiveData<FavModelResponse> result = new MutableLiveData<>();
        mCallFavorite = RestClient.getInstance().getApiClient().favorite(new FavModelRequest(realEstateId, userId, locale));
        mCallFavorite.enqueue(new Callback<FavModelResponse>() {
            @Override
            public void onResponse(Call<FavModelResponse> call, Response<FavModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return result;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
