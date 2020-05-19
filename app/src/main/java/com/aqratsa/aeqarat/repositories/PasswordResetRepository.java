package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.password_reset.request.ResetPasswordModelRequest;
import com.aqratsa.aeqarat.models.password_reset.response.ResetPasswordModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordResetRepository {

    private static PasswordResetRepository instance;
    private Call<ResetPasswordModelResponse> mCallResetPassword;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static PasswordResetRepository getInstance(){
        if (instance == null){
            instance = new PasswordResetRepository();
        }
        return instance;
    }

    public MutableLiveData<ResetPasswordModelResponse> resetPassword(String email,
                                                                     String newPassword,
                                                                     String confirmPassword,
                                                                     String locale){
        mLoading.setValue(true);
        MutableLiveData<ResetPasswordModelResponse> result = new MutableLiveData<>();
        mCallResetPassword = RestClient.getInstance().getApiClient()
                .resetPassword(new ResetPasswordModelRequest(email, newPassword, confirmPassword, locale));
        mCallResetPassword.enqueue(new Callback<ResetPasswordModelResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordModelResponse> call, Response<ResetPasswordModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordModelResponse> call, Throwable t) {
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
