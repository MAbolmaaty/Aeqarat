package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.request_ownership.request.RequestOwnershipModelRequest;
import com.aqratsa.aeqarat.models.request_ownership.response.RequestOwnershipModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestOwnershipRepository {

    private static RequestOwnershipRepository instance;
    private Call<RequestOwnershipModelResponse> mCallOwnershipRequest;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestOwnershipRepository getInstance() {
        if (instance == null) {
            instance = new RequestOwnershipRepository();
        }
        return instance;
    }

    public MutableLiveData<RequestOwnershipModelResponse> ownershipRequest(String realEstateId,
                                                                           String userId, String startDate, String payment, String locale) {
        mLoading.setValue(true);
        MutableLiveData<RequestOwnershipModelResponse> result = new MutableLiveData<>();
        mCallOwnershipRequest = RestClient.getInstance().getApiClient()
                .requestOwnership(new RequestOwnershipModelRequest(realEstateId, userId,
                        startDate, payment, locale));
        mCallOwnershipRequest.enqueue(new Callback<RequestOwnershipModelResponse>() {
            @Override
            public void onResponse(Call<RequestOwnershipModelResponse> call, Response<RequestOwnershipModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestOwnershipModelResponse> call, Throwable t) {
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
