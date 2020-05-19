package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.request_ownership.response.RequestOwnershipModelResponse;
import com.aqratsa.aeqarat.repositories.RequestOwnershipRepository;

public class RequestOwnershipViewModel extends ViewModel {

    private RequestOwnershipRepository mRepositoryOwnershipRequest;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<RequestOwnershipModelResponse> mResult;
    private MutableLiveData<Boolean> mFailure;

    public void ownershipRequest(String realEstateId,
                                 String userId, String startDate, String payment, String locale){
        if (mRepositoryOwnershipRequest != null)
            return;
        mRepositoryOwnershipRequest = RequestOwnershipRepository.getInstance();
        mResult = mRepositoryOwnershipRequest.ownershipRequest(realEstateId, userId, startDate,
                payment, locale);
        mLoading = mRepositoryOwnershipRequest.loading();
        mFailure = mRepositoryOwnershipRequest.failure();
    }

    public LiveData<RequestOwnershipModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
