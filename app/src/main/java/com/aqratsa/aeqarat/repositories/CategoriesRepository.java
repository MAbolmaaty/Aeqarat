package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.real_estate_categories.RealEstateCategoriesModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    private static CategoriesRepository instance;
    private Call<RealEstateCategoriesModelResponse> mCallCategories;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static CategoriesRepository getInstance(){
        if (instance == null){
            instance = new CategoriesRepository();
        }
        return instance;
    }

    public MutableLiveData<RealEstateCategoriesModelResponse> getCategories(String locale){
        mLoading.setValue(true);
        MutableLiveData<RealEstateCategoriesModelResponse> categories = new MutableLiveData<>();
        mCallCategories = RestClient.getInstance().getApiClient().getCategories(locale);
        mCallCategories.enqueue(new Callback<RealEstateCategoriesModelResponse>() {
            @Override
            public void onResponse(Call<RealEstateCategoriesModelResponse> call, Response<RealEstateCategoriesModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    categories.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RealEstateCategoriesModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return categories;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
