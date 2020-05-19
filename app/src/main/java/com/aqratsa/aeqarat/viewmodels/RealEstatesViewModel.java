package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.real_estates.response.RealEstatesModelResponse;
import com.aqratsa.aeqarat.repositories.RealEstatesRepository;

// Leaking
public class RealEstatesViewModel extends ViewModel {

    private MutableLiveData<RealEstatesModelResponse> mRealEstates;
    private MutableLiveData<String> realEstate = new MutableLiveData<>();
    private MutableLiveData<Boolean> mMapFragment = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void realEstates(String sale, String rent, String auction) {
        RealEstatesRepository repositoryRealEstates = RealEstatesRepository.getInstance();
        mRealEstates = repositoryRealEstates.getRealEstates(sale, rent, auction);
        mLoading = repositoryRealEstates.loading();
        mFailure = repositoryRealEstates.failure();
    }

    public LiveData<RealEstatesModelResponse> getRealEstates() {
        return mRealEstates;
    }

    public void select(String realEstateId){
        realEstate.setValue(realEstateId);
    }

    public LiveData<String> getSelectedId(){
        return realEstate;
    }

    public void startMapFragment(boolean start){
        mMapFragment.setValue(start);
    }

    public MutableLiveData<Boolean> isMapFragment(){
        return mMapFragment;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
