package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.search.request.SearchModelRequest;
import com.aqratsa.aeqarat.models.search.response.SearchModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private static final String TAG = SearchRepository.class.getSimpleName();

    private static SearchRepository instance;
    private Call<SearchModelResponse> mCallSearch;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static SearchRepository getInstance(){
        if (instance == null){
            instance = new SearchRepository();
        }
        return instance;
    }

    public MutableLiveData<SearchModelResponse> search(String category, String status,
                                                       String minPrice, String maxPrice,
                                                       String address, String region,
                                                       String district, String age,
                                                       String minArea, String maxArea,
                                                       int regionItemPosition,
                                                       int districtItemPosition){

        mLoading.setValue(true);
        MutableLiveData<SearchModelResponse> results = new MutableLiveData<>();
        mCallSearch = RestClient.getInstance().getApiClient()
                .search(new SearchModelRequest(category, status, minPrice, maxPrice,
                        address, region, district, age, minArea, maxArea, regionItemPosition,
                        districtItemPosition));
        mCallSearch.enqueue(new Callback<SearchModelResponse>() {
            @Override
            public void onResponse(Call<SearchModelResponse> call, Response<SearchModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    results.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return results;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
