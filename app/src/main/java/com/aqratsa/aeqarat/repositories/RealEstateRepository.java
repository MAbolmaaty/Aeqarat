package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estate.RealEstateModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstateRepository {

    private static final String TAG = RealEstateRepository.class.getSimpleName();

    private static RealEstateRepository instance;
    private Call<RealEstateModelResponse> mCallRealEstate;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RealEstateRepository getInstance(){
        if (instance == null){
            instance = new RealEstateRepository();
        }
        return instance;
    }

    public MutableLiveData<RealEstateModelResponse> realEstate(String realEstateId){
        mLoading.setValue(true);
        MutableLiveData<RealEstateModelResponse> realEstateDetails = new MutableLiveData<>();
        mCallRealEstate = RestClient.getInstance().getApiClient().getRealEstate(realEstateId);
        mCallRealEstate.enqueue(new Callback<RealEstateModelResponse>() {
            @Override
            public void onResponse(Call<RealEstateModelResponse> call, Response<RealEstateModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    realEstateDetails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RealEstateModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return realEstateDetails;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
