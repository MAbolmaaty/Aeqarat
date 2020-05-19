package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estate_requests_user.RealEstateRequestsUserModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstateRequestsUserRepository {

    private static RealEstateRequestsUserRepository mInstance;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RealEstateRequestsUserRepository getInstance(){
        if (mInstance == null){
            mInstance = new RealEstateRequestsUserRepository();
        }
        return mInstance;
    }

    public MutableLiveData<RealEstateRequestsUserModelResponse> realEstateRequests(String realEstateId){
        mLoading.setValue(true);
        MutableLiveData<RealEstateRequestsUserModelResponse> requests = new MutableLiveData<>();
        Call<RealEstateRequestsUserModelResponse> callRealEstateRequests =
                RestClient.getInstance().getApiClient().realEstateRequests(realEstateId);
        callRealEstateRequests.enqueue(new Callback<RealEstateRequestsUserModelResponse>() {
            @Override
            public void onResponse(Call<RealEstateRequestsUserModelResponse> call, Response<RealEstateRequestsUserModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    requests.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RealEstateRequestsUserModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return requests;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
