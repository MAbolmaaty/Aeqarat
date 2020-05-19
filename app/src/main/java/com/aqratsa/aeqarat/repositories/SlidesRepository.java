package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.slides.SlidesModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlidesRepository {

    private static final String TAG = SlidesRepository.class.getSimpleName();

    private static SlidesRepository instance;
    private Call<SlidesModelResponse> mCallSlides;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static SlidesRepository getInstance(){
        if (instance == null){
            instance = new SlidesRepository();
        }
        return instance;
    }

    public MutableLiveData<SlidesModelResponse> getSlides(){
        mLoading.setValue(true);
        mFailure.setValue(false);
        MutableLiveData<SlidesModelResponse> slides = new MutableLiveData<>();
        mCallSlides = RestClient.getInstance().getApiClient().getSlides();
        mCallSlides.enqueue(new Callback<SlidesModelResponse>() {
            @Override
            public void onResponse(Call<SlidesModelResponse> call, Response<SlidesModelResponse> response) {
                mLoading.setValue(false);
                if (response.body() != null && response.body().getSlide() != null){
                    slides.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SlidesModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return slides;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
