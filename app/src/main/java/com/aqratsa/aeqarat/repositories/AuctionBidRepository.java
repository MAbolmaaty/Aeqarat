package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.auction_bid.request.AuctionBidModelRequest;
import com.aqratsa.aeqarat.models.auction_bid.response.AuctionBidModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionBidRepository {

    private static AuctionBidRepository mInstance;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static AuctionBidRepository getInstance(){
        if (mInstance == null){
            mInstance = new AuctionBidRepository();
        }
        return mInstance;
    }

    public MutableLiveData<AuctionBidModelResponse> auctionBet(String realEstateId, String userId,
                                                               String amount, String locale){
        mLoading.setValue(true);
        MutableLiveData<AuctionBidModelResponse> result = new MutableLiveData<>();
        Call<AuctionBidModelResponse> callAuctionBet = RestClient.getInstance().getApiClient()
                .bet(new AuctionBidModelRequest(realEstateId, userId, amount, locale));
        callAuctionBet.enqueue(new Callback<AuctionBidModelResponse>() {
            @Override
            public void onResponse(Call<AuctionBidModelResponse> call, Response<AuctionBidModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuctionBidModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return result;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
