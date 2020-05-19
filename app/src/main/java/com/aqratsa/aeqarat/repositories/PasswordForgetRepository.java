package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.password_forget.request.ForgetPasswordModelRequest;
import com.aqratsa.aeqarat.models.password_forget.response.ForgetPasswordModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordForgetRepository {

    private static PasswordForgetRepository instance;
    private Call<ForgetPasswordModelResponse> mCallForgetPassword;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static PasswordForgetRepository getInstance(){
        if (instance == null){
            instance = new PasswordForgetRepository();
        }
        return instance;
    }

    public MutableLiveData<ForgetPasswordModelResponse> forgetPassword(String email, String locale){
        mLoading.setValue(true);
        MutableLiveData<ForgetPasswordModelResponse> result = new MutableLiveData<>();
        mCallForgetPassword = RestClient.getInstance().getApiClient()
                .forgetPassword(new ForgetPasswordModelRequest(email, locale));
        mCallForgetPassword.enqueue(new Callback<ForgetPasswordModelResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordModelResponse> call, Response<ForgetPasswordModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordModelResponse> call, Throwable t) {
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
