package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.payment_card_default.PaymentCardDefaultModelResponse;
import com.aqratsa.aeqarat.repositories.PaymentCardDefaultRepository;

public class PaymentCardDefaultViewModel extends ViewModel {

    private MutableLiveData<PaymentCardDefaultModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void defaultCard(String cardId){
        PaymentCardDefaultRepository repositoryDefaultCard = PaymentCardDefaultRepository.getInstance();
        mResult = repositoryDefaultCard.defaultCard(cardId);
        mLoading = repositoryDefaultCard.loading();
        mFailure = repositoryDefaultCard.failure();
    }

    public LiveData<PaymentCardDefaultModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }
    public LiveData<Boolean> failure(){return mFailure;}
}
