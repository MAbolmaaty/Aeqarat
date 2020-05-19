package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.privacy_policy.PrivacyPolicyModelResponse;
import com.aqratsa.aeqarat.repositories.PrivacyPolicyRepository;

public class PrivacyPolicyViewModel extends ViewModel {

    private MutableLiveData<PrivacyPolicyModelResponse> mPrivacyPolicy;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void privacyPolicy() {
        PrivacyPolicyRepository repositoryPrivacyPolicy = PrivacyPolicyRepository.getInstance();
        mPrivacyPolicy = repositoryPrivacyPolicy.privacyPolicy();
        mLoading = repositoryPrivacyPolicy.loading();
        mFailure = repositoryPrivacyPolicy.failure();
    }

    public LiveData<PrivacyPolicyModelResponse> getPrivacyPolicy() {
        return mPrivacyPolicy;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
