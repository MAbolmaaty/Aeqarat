package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.request_termination.response.RequestTerminationModelResponse;
import com.aqratsa.aeqarat.repositories.RequestTerminationRepository;

public class RequestTerminationViewModel extends ViewModel {

    private RequestTerminationRepository mRepositoryTermination;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<RequestTerminationModelResponse> mResult;
    private MutableLiveData<Boolean> mFailure;

    public void terminateContract(String realEstateId,
                                  String userId, String exitDate,
                                  String refundMethod, String locale){

        if (mRepositoryTermination != null)
            return;
        mRepositoryTermination = RequestTerminationRepository.getInstance();
        mResult = mRepositoryTermination.terminate(realEstateId, userId,
                exitDate, refundMethod, locale);
        mLoading = mRepositoryTermination.loading();
        mFailure = mRepositoryTermination.failure();
    }

    public LiveData<RequestTerminationModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
