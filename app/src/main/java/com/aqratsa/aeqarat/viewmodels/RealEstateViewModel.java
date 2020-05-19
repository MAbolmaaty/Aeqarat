package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.real_estate.RealEstateModelResponse;
import com.aqratsa.aeqarat.repositories.RealEstateRepository;

// Leaking
public class RealEstateViewModel extends ViewModel {

    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<RealEstateModelResponse> mRealEstate;
    private MutableLiveData<String> mRealEstateId = new MutableLiveData<>();
    private MutableLiveData<Integer> mTypeRequest = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure;

    public void realEstate(String realEstateId) {
        RealEstateRepository repositoryRealEstate = RealEstateRepository.getInstance();
        mRealEstate = repositoryRealEstate.realEstate(realEstateId);
        mLoading = repositoryRealEstate.loading();
        mFailure = repositoryRealEstate.failure();
    }

    public void setRealEstateId(String id){
        mRealEstateId.setValue(id);
    }

    public LiveData<RealEstateModelResponse> getRealEstate() {
        return mRealEstate;
    }

    public void setTypeRequest(int type){
        mTypeRequest.setValue(type);
    }

    public LiveData<Integer> getTypeRequest(){
        return mTypeRequest;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<String> getRealEstateId(){
        return mRealEstateId;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
