package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.districts.DistrictsModelResponse;
import com.aqratsa.aeqarat.repositories.DistrictsRepository;

public class DistrictsViewModel extends ViewModel {

    private MutableLiveData<DistrictsModelResponse> mDistricts;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void districts(String locale){
        DistrictsRepository repositoryDistricts = DistrictsRepository.getInstance();
        mDistricts = repositoryDistricts.getDistricts(locale);
        mLoading = repositoryDistricts.loading();
        mFailure = repositoryDistricts.failure();
    }

    public LiveData<DistrictsModelResponse> getDistricts(){
        return mDistricts;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
