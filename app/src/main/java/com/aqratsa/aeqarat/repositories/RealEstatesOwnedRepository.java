package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estates_owned.RealEstatesOwnedModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstatesOwnedRepository {

    private static RealEstatesOwnedRepository instance;
    private Call<RealEstatesOwnedModelResponse> mCallMyRealEstates;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RealEstatesOwnedRepository getInstance(){
        if (instance == null){
            instance = new RealEstatesOwnedRepository();
        }
        return instance;
    }

    public MutableLiveData<RealEstatesOwnedModelResponse> myRealEstates(String userId){
        mLoading.setValue(true);
        MutableLiveData<RealEstatesOwnedModelResponse> myRealEstates = new MutableLiveData<>();
        mCallMyRealEstates = RestClient.getInstance().getApiClient().getRealEstatesOwned(userId);
        mCallMyRealEstates.enqueue(new Callback<RealEstatesOwnedModelResponse>() {
            @Override
            public void onResponse(Call<RealEstatesOwnedModelResponse> call, Response<RealEstatesOwnedModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    myRealEstates.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RealEstatesOwnedModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return myRealEstates;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
