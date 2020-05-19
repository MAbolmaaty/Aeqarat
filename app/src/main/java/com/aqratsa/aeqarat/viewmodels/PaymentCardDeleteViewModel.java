package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.payment_card_delete.PaymentCardDeleteModelResponse;
import com.aqratsa.aeqarat.repositories.PaymentCardDeleteRepository;

public class PaymentCardDeleteViewModel extends ViewModel {

    private MutableLiveData<PaymentCardDeleteModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void deleteCard(String cardId){
        PaymentCardDeleteRepository repositoryDeleteCard = PaymentCardDeleteRepository.getInstance();
        mResult = repositoryDeleteCard.deleteCard(cardId);
        mLoading = repositoryDeleteCard.loading();
        mFailure = repositoryDeleteCard.failure();
    }

    public LiveData<PaymentCardDeleteModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }
    public LiveData<Boolean> failure(){return mFailure;}
}
