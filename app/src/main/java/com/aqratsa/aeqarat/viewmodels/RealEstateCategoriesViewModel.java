package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.real_estate_categories.RealEstateCategoriesModelResponse;
import com.aqratsa.aeqarat.repositories.CategoriesRepository;

// Leaking
public class RealEstateCategoriesViewModel extends ViewModel {

    private MutableLiveData<RealEstateCategoriesModelResponse> mCategories;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void categories(String locale){
        CategoriesRepository repositoryCategories = CategoriesRepository.getInstance();
        mCategories = repositoryCategories.getCategories(locale);
        mLoading = repositoryCategories.loading();
        mFailure = repositoryCategories.failure();
    }

    public LiveData<RealEstateCategoriesModelResponse> getCategories(){
        return mCategories;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
