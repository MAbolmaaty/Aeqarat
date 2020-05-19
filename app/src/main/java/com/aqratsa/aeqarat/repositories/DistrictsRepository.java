package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.districts.DistrictsModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictsRepository {

    private static DistrictsRepository instance;
    private Call<DistrictsModelResponse> mCallDistricts;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static DistrictsRepository getInstance(){
        if (instance == null){
            instance = new DistrictsRepository();
        }
        return instance;
    }

    public MutableLiveData<DistrictsModelResponse> getDistricts(String locale){
        mLoading.setValue(true);
        MutableLiveData<DistrictsModelResponse> districts = new MutableLiveData<>();
        mCallDistricts = RestClient.getInstance().getApiClient().getDistricts(locale);
        mCallDistricts.enqueue(new Callback<DistrictsModelResponse>() {
            @Override
            public void onResponse(Call<DistrictsModelResponse> call, Response<DistrictsModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    districts.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DistrictsModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return districts;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
