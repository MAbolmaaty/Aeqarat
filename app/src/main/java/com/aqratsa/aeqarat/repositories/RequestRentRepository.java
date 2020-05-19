package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.request_rent.request.RequestRentModelRequest;
import com.aqratsa.aeqarat.models.request_rent.response.RequestRentModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestRentRepository {

    private static RequestRentRepository mInstance;
    private Call<RequestRentModelResponse> mCallRentRequest;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestRentRepository getInstance() {
        if (mInstance == null) {
            mInstance = new RequestRentRepository();
        }
        return mInstance;
    }

    public MutableLiveData<RequestRentModelResponse> rentRequest(String realEstateId,
                                                                 String userId, String startDate, String duration, String payment, String locale) {

        mLoading.setValue(true);
        MutableLiveData<RequestRentModelResponse> result = new MutableLiveData<>();
        mCallRentRequest = RestClient.getInstance().getApiClient()
                .requestRent(new RequestRentModelRequest(realEstateId, userId,
                        startDate, duration, payment, locale));
        mCallRentRequest.enqueue(new Callback<RequestRentModelResponse>() {
            @Override
            public void onResponse(Call<RequestRentModelResponse> call, Response<RequestRentModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestRentModelResponse> call, Throwable t) {
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
