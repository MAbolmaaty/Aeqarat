package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.request_submitted.RequestSubmittedModelResponse;
import com.aqratsa.aeqarat.repositories.RequestSubmittedRepository;

// Leaking
public class RequestSubmittedViewModel extends ViewModel {

    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<RequestSubmittedModelResponse> mResult;
    private MutableLiveData<String> mRequestId = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure;

    public void submittedRequest(String requestId) {
        RequestSubmittedRepository repositorySubmittedRequest = RequestSubmittedRepository.getInstance();
        mResult = repositorySubmittedRequest.submittedRequest(requestId);
        mLoading = repositorySubmittedRequest.loading();
        mFailure = repositorySubmittedRequest.failure();
    }

    public void setRequestId(String requestId) {
        mRequestId.setValue(requestId);
    }

    public LiveData<String> getRequestId() {
        return mRequestId;
    }

    public LiveData<RequestSubmittedModelResponse> getResult() {
        return mResult;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
