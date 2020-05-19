package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.email_confirm.request.ConfirmEmailModelRequest;
import com.aqratsa.aeqarat.models.email_confirm.response.ConfirmEmailModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailConfirmRepository {

    private static EmailConfirmRepository instance;
    private Call<ConfirmEmailModelResponse> mCallConfirmEmail;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static EmailConfirmRepository getInstance(){
        if (instance == null){
            instance = new EmailConfirmRepository();
        }
        return instance;
    }

    public MutableLiveData<ConfirmEmailModelResponse> confirmEmail(String email, String locale){
        mLoading.setValue(true);
        MutableLiveData<ConfirmEmailModelResponse> result = new MutableLiveData<>();
        mCallConfirmEmail = RestClient.getInstance().getApiClient().confirmEmail(new ConfirmEmailModelRequest(email, locale));
        mCallConfirmEmail.enqueue(new Callback<ConfirmEmailModelResponse>() {
            @Override
            public void onResponse(Call<ConfirmEmailModelResponse> call, Response<ConfirmEmailModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ConfirmEmailModelResponse> call, Throwable t) {
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
