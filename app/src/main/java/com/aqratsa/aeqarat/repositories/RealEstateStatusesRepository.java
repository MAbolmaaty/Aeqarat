package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estate_statuses.RealEstateStatusesModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstateStatusesRepository {

    private static RealEstateStatusesRepository instance;
    private Call<RealEstateStatusesModelResponse> mCallStatuses;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RealEstateStatusesRepository getInstance(){
        if (instance == null){
            instance = new RealEstateStatusesRepository();
        }
        return instance;
    }

    public MutableLiveData<RealEstateStatusesModelResponse> getStatuses(String locale){
        mLoading.setValue(true);
        MutableLiveData<RealEstateStatusesModelResponse> statuses = new MutableLiveData<>();
        mCallStatuses = RestClient.getInstance().getApiClient().getStatuses(locale);
        mCallStatuses.enqueue(new Callback<RealEstateStatusesModelResponse>() {
            @Override
            public void onResponse(Call<RealEstateStatusesModelResponse> call, Response<RealEstateStatusesModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    statuses.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RealEstateStatusesModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return statuses;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
