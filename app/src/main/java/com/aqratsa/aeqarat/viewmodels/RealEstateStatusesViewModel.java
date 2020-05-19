package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.real_estate_statuses.RealEstateStatusesModelResponse;
import com.aqratsa.aeqarat.repositories.RealEstateStatusesRepository;

// Leaking
public class RealEstateStatusesViewModel extends ViewModel {

    private MutableLiveData<RealEstateStatusesModelResponse> mStatuses;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void statuses(String locale){
        RealEstateStatusesRepository repositoryStatuses = RealEstateStatusesRepository.getInstance();
        mStatuses = repositoryStatuses.getStatuses(locale);
        mLoading = repositoryStatuses.loading();
        mFailure = repositoryStatuses.failure();
    }

    public LiveData<RealEstateStatusesModelResponse> getStatuses(){
        return mStatuses;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
