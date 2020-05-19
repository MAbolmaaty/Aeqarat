package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estates.request.RealEstatesModelRequest;
import com.aqratsa.aeqarat.models.real_estates.response.RealEstatesModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstatesRepository {

    private static final String TAG = RealEstatesRepository.class.getSimpleName();

    private static RealEstatesRepository instance;
    private Call<RealEstatesModelResponse> mCallRealEstates;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RealEstatesRepository getInstance(){
        if (instance == null){
            instance = new RealEstatesRepository();
        }
        return instance;
    }

    public MutableLiveData<RealEstatesModelResponse> getRealEstates(String sale, String rent, String auction){
        mLoading.setValue(true);
        MutableLiveData<RealEstatesModelResponse> realEstates = new MutableLiveData<>();
        mCallRealEstates = RestClient.getInstance().getApiClient().getRealEstates(new RealEstatesModelRequest(sale, rent, auction));
        mCallRealEstates.enqueue(new Callback<RealEstatesModelResponse>() {
            @Override
            public void onResponse(Call<RealEstatesModelResponse> call, Response<RealEstatesModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    realEstates.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RealEstatesModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return realEstates;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
