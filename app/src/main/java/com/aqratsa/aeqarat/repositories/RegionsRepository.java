package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.regions.RegionsModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegionsRepository {

    private static RegionsRepository instance;
    private Call<RegionsModelResponse> mCallRegions;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RegionsRepository getInstance(){
        if (instance == null){
            instance = new RegionsRepository();
        }
        return instance;
    }

    public MutableLiveData<RegionsModelResponse> getRegions(String locale){
        mLoading.setValue(true);
        MutableLiveData<RegionsModelResponse> regions = new MutableLiveData<>();
        mCallRegions = RestClient.getInstance().getApiClient().getRegions(locale);
        mCallRegions.enqueue(new Callback<RegionsModelResponse>() {
            @Override
            public void onResponse(Call<RegionsModelResponse> call, Response<RegionsModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    regions.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RegionsModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return regions;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
