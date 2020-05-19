package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.request_termination.request.RequestTerminationModelRequest;
import com.aqratsa.aeqarat.models.request_termination.response.RequestTerminationModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestTerminationRepository {

    private static RequestTerminationRepository mInstance;
    private Call<RequestTerminationModelResponse> mCallRequestTermination;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestTerminationRepository getInstance(){
        if (mInstance == null){
            mInstance = new RequestTerminationRepository();
        }
        return mInstance;
    }

    public MutableLiveData<RequestTerminationModelResponse> terminate(String realEstateId,
                                                                      String userId, String exitDate,
                                                                      String refundMethod, String locale){
        mLoading.setValue(true);
        MutableLiveData<RequestTerminationModelResponse> result = new MutableLiveData<>();
        mCallRequestTermination = RestClient.getInstance().getApiClient()
                .terminateContract(new RequestTerminationModelRequest(realEstateId, userId,
                        exitDate, refundMethod, locale));
        mCallRequestTermination.enqueue(new Callback<RequestTerminationModelResponse>() {
            @Override
            public void onResponse(Call<RequestTerminationModelResponse> call, Response<RequestTerminationModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestTerminationModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return result;
    }

    public MutableLiveData<Boolean> loading() {
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
