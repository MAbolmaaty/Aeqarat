package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.requests_user.UserRequestsModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsUserRepository {

    private static RequestsUserRepository instance;
    private Call<UserRequestsModelResponse> mCallUserRequests;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestsUserRepository getInstance(){
        if (instance == null){
            instance = new RequestsUserRepository();
        }
        return instance;
    }

    public MutableLiveData<UserRequestsModelResponse> userRequests(String userId){
        mLoading.setValue(true);
        MutableLiveData<UserRequestsModelResponse> userRequests = new MutableLiveData<>();
        mCallUserRequests = RestClient.getInstance().getApiClient().getUserRequests(userId);
        mCallUserRequests.enqueue(new Callback<UserRequestsModelResponse>() {
            @Override
            public void onResponse(Call<UserRequestsModelResponse> call, Response<UserRequestsModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    userRequests.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserRequestsModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return userRequests;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
