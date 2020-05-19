package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.payment_card_add.response.PaymentCardAddModelResponse;
import com.aqratsa.aeqarat.repositories.PaymentCardAddRepository;

public class PaymentCardAddViewModel extends ViewModel {

    private MutableLiveData<PaymentCardAddModelResponse> mPaymentCard;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void paymentCard(String cardNumber,
                            String expireDate,
                            String userId,
                            String type,
                            String locale,
                            String cvv,
                            String status) {

        PaymentCardAddRepository repositoryPaymentCardAdd = PaymentCardAddRepository.getInstance();
        mPaymentCard = repositoryPaymentCardAdd.addPaymentCard(cardNumber, expireDate, userId,
                type, locale, cvv, status);
        mLoading = repositoryPaymentCardAdd.loading();
        mFailure = repositoryPaymentCardAdd.failure();
    }

    public LiveData<PaymentCardAddModelResponse> getPaymentCard() {
        return mPaymentCard;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
