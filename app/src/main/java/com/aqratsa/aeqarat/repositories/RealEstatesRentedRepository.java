package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estates_rented.RentedRealEstatesModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstatesRentedRepository {

    private static RealEstatesRentedRepository mInstance;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RealEstatesRentedRepository getInstance(){
        if (mInstance == null){
            mInstance = new RealEstatesRentedRepository();
        }
        return mInstance;
    }

    public MutableLiveData<RentedRealEstatesModelResponse> rentedRealEstates(String userId){
        mLoading.setValue(true);
        MutableLiveData<RentedRealEstatesModelResponse> realEstates = new MutableLiveData<>();
        Call<RentedRealEstatesModelResponse> callRentedRealEstates =
                RestClient.getInstance().getApiClient().getRentedRealEstates(userId);
        callRentedRealEstates.enqueue(new Callback<RentedRealEstatesModelResponse>() {
            @Override
            public void onResponse(Call<RentedRealEstatesModelResponse> call, Response<RentedRealEstatesModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    realEstates.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RentedRealEstatesModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return realEstates;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
