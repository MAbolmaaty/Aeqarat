package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.slides.SlidesModelResponse;
import com.aqratsa.aeqarat.repositories.SlidesRepository;

// Leaking
public class SlidesViewModel extends ViewModel {

    private static final String TAG = SlidesViewModel.class.getSimpleName();

    private MutableLiveData<SlidesModelResponse> slides;
    private SlidesRepository mRepositorySlides;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void slides(){
        if (slides != null){
            return;
        }
        mRepositorySlides = SlidesRepository.getInstance();
        slides = mRepositorySlides.getSlides();
        mLoading = mRepositorySlides.loading();
        mFailure = mRepositorySlides.failure();
    }

    public LiveData<SlidesModelResponse> getSlides(){
        return slides;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
