package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.request_modify.request.ModifyRequestModelRequest;
import com.aqratsa.aeqarat.models.request_modify.response.ModifyRequestModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestModifyRepository {

    private static RequestModifyRepository instance;
    private Call<ModifyRequestModelResponse> mCallModifyRequest;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestModifyRepository getInstance(){
        if (instance == null){
            instance = new RequestModifyRepository();
        }
        return instance;
    }

    public MutableLiveData<ModifyRequestModelResponse> modifyRequest(String startDate,
            String duration, String payment, String requestId, String locale){

        mLoading.setValue(true);
        MutableLiveData<ModifyRequestModelResponse> result = new MutableLiveData<>();
        mCallModifyRequest = RestClient.getInstance().getApiClient()
                .modifyRequest(new ModifyRequestModelRequest(startDate, duration, payment, requestId, locale));
        mCallModifyRequest.enqueue(new Callback<ModifyRequestModelResponse>() {
            @Override
            public void onResponse(Call<ModifyRequestModelResponse> call, Response<ModifyRequestModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModifyRequestModelResponse> call, Throwable t) {
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

