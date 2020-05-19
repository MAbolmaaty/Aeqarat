package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.request_modify.response.ModifyRequestModelResponse;
import com.aqratsa.aeqarat.repositories.RequestModifyRepository;

public class RequestModifyViewModel extends ViewModel {

    private RequestModifyRepository mRepositoryModifyRequest;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<ModifyRequestModelResponse> mResult;
    private MutableLiveData<Boolean> mFailure;

    public void modifyRequest(String startDate,
                              String duration, String payment, String requestId, String locale) {
        if (mRepositoryModifyRequest != null)
            return;
        mRepositoryModifyRequest = RequestModifyRepository.getInstance();
        mResult = mRepositoryModifyRequest.modifyRequest(startDate, duration, payment, requestId, locale);
        mLoading = mRepositoryModifyRequest.loading();
        mFailure = mRepositoryModifyRequest.failure();
    }

    public LiveData<ModifyRequestModelResponse> getResult() {
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
