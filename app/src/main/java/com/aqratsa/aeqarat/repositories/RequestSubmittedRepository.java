package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.request_submitted.RequestSubmittedModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestSubmittedRepository {

    private static RequestSubmittedRepository instance;
    private Call<RequestSubmittedModelResponse> mCallSubmittedRequest;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestSubmittedRepository getInstance() {
        if (instance == null){
            instance = new RequestSubmittedRepository();
        }
        return instance;
    }

    public MutableLiveData<RequestSubmittedModelResponse> submittedRequest(String requestId){

        mLoading.setValue(true);
        MutableLiveData<RequestSubmittedModelResponse> result = new MutableLiveData<>();
        mCallSubmittedRequest = RestClient.getInstance().getApiClient().getSubmittedRequest(requestId);
        mCallSubmittedRequest.enqueue(new Callback<RequestSubmittedModelResponse>() {
            @Override
            public void onResponse(Call<RequestSubmittedModelResponse> call, Response<RequestSubmittedModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestSubmittedModelResponse> call, Throwable t) {
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
