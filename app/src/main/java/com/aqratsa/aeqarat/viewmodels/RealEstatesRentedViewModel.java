package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.real_estates_rented.RealEstate;
import com.aqratsa.aeqarat.models.real_estates_rented.RentedRealEstatesModelResponse;
import com.aqratsa.aeqarat.repositories.RealEstatesRentedRepository;

public class RealEstatesRentedViewModel extends ViewModel {

    private MutableLiveData<RentedRealEstatesModelResponse> mRentedRealEstates;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void rentedRealEstates(String userId) {
        RealEstatesRentedRepository repositoryRentedRealEstates = RealEstatesRentedRepository.getInstance();
        mRentedRealEstates = repositoryRentedRealEstates.rentedRealEstates(userId);
        mLoading = repositoryRentedRealEstates.loading();
        mFailure = repositoryRentedRealEstates.failure();
    }

    public LiveData<RentedRealEstatesModelResponse> getRentedRealEstates() {
        return mRentedRealEstates;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }
    public LiveData<Boolean> failure(){return mFailure;}
}
