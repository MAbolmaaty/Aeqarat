package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.request_rent.response.RequestRentModelResponse;
import com.aqratsa.aeqarat.repositories.RequestRentRepository;

public class RequestRentViewModel extends ViewModel {

    private RequestRentRepository mRepositoryRentRequest;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<RequestRentModelResponse> mResult;
    private MutableLiveData<Boolean> mFailure;

    public void rentRequest(String realEstateId,
                            String userId, String startDate, String duration, String payment, String locale) {
        if (mRepositoryRentRequest != null)
            return;
        mRepositoryRentRequest = RequestRentRepository.getInstance();
        mResult = mRepositoryRentRequest.rentRequest(realEstateId, userId, startDate,
                duration, payment, locale);
        mLoading = mRepositoryRentRequest.loading();
        mFailure = mRepositoryRentRequest.failure();
    }

    public LiveData<RequestRentModelResponse> getResult() {
        return mResult;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
