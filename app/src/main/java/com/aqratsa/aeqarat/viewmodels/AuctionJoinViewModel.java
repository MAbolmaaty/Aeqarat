package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.auction_join.AuctionJoinModelResponse;
import com.aqratsa.aeqarat.repositories.AuctionJoinRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AuctionJoinViewModel extends ViewModel {

    private MutableLiveData<AuctionJoinModelResponse> mData;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void joinAuction(MultipartBody.Part file,
                            RequestBody userId,
                            RequestBody realEstateId){
        AuctionJoinRepository repositoryAuctionJoin = AuctionJoinRepository.getInstance();
        mData = repositoryAuctionJoin.joinAuction(file, userId, realEstateId);
        mLoading = repositoryAuctionJoin.loading();
        mFailure = repositoryAuctionJoin.failure();
    }

    public LiveData<AuctionJoinModelResponse> getData(){
        return mData;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
