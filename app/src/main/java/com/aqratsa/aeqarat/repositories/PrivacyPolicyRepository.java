package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.privacy_policy.PrivacyPolicyModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyRepository {

    private static PrivacyPolicyRepository instance;
    private Call<PrivacyPolicyModelResponse> mCallPrivacyPolicy;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static PrivacyPolicyRepository getInstance() {
        if (instance == null) {
            instance = new PrivacyPolicyRepository();
        }
        return instance;
    }

    public MutableLiveData<PrivacyPolicyModelResponse> privacyPolicy() {
        mLoading.setValue(true);
        MutableLiveData<PrivacyPolicyModelResponse> results = new MutableLiveData<>();
        mCallPrivacyPolicy = RestClient.getInstance().getApiClient().getPrivacyPolicies();
        mCallPrivacyPolicy.enqueue(new Callback<PrivacyPolicyModelResponse>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModelResponse> call, Response<PrivacyPolicyModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    results.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PrivacyPolicyModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return results;
    }

    public MutableLiveData<Boolean> loading() {
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
