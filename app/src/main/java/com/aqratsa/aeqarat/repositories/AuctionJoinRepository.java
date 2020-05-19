package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.auction_join.AuctionJoinModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionJoinRepository {

    private static final String TAG = AuctionJoinRepository.class.getSimpleName();

    private static AuctionJoinRepository mInstance;
    private Call<AuctionJoinModelResponse> mCallAuctionJoin;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static AuctionJoinRepository getInstance(){
        if (mInstance == null){
            mInstance = new AuctionJoinRepository();
        }
        return mInstance;
    }

    public MutableLiveData<AuctionJoinModelResponse> joinAuction(MultipartBody.Part file,
                                                                 RequestBody userId,
                                                                 RequestBody realEstateId){
        mLoading.setValue(true);
        MutableLiveData<AuctionJoinModelResponse> data = new MutableLiveData<>();
        mCallAuctionJoin = RestClient.getInstance().getApiClient().joinAuction(file, userId, realEstateId);
        mCallAuctionJoin.enqueue(new Callback<AuctionJoinModelResponse>() {
            @Override
            public void onResponse(Call<AuctionJoinModelResponse> call, Response<AuctionJoinModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuctionJoinModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });

        return data;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
