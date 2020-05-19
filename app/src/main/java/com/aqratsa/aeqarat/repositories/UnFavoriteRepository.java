package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.unfavorite.request.UnFavoriteModelRequest;
import com.aqratsa.aeqarat.models.unfavorite.response.UnFavoriteModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnFavoriteRepository {

    private static UnFavoriteRepository instance;
    private Call<UnFavoriteModelResponse> mCallUnFavorite;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static UnFavoriteRepository getInstance(){
        if (instance == null){
            instance = new UnFavoriteRepository();
        }
        return instance;
    }

    public MutableLiveData<UnFavoriteModelResponse> unFavorite(String realEstateId, String userId, String locale){
        mLoading.setValue(true);
        MutableLiveData<UnFavoriteModelResponse> result = new MutableLiveData<>();
        mCallUnFavorite = RestClient.getInstance().getApiClient()
                .unFavorite(new UnFavoriteModelRequest(realEstateId, userId, locale));
        mCallUnFavorite.enqueue(new Callback<UnFavoriteModelResponse>() {
            @Override
            public void onResponse(Call<UnFavoriteModelResponse> call, Response<UnFavoriteModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UnFavoriteModelResponse> call, Throwable t) {
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
