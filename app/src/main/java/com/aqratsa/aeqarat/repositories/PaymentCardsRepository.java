package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.payment_cards.PaymentCardsModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCardsRepository {

    private static PaymentCardsRepository mInstance;
    private Call<PaymentCardsModelResponse> mCallPaymentCards;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static PaymentCardsRepository getInstance(){
        if (mInstance == null){
            mInstance = new PaymentCardsRepository();
        }
        return mInstance;
    }

    public MutableLiveData<PaymentCardsModelResponse> getPaymentCards(String userId){
        mLoading.setValue(true);
        MutableLiveData<PaymentCardsModelResponse> paymentCards = new MutableLiveData<>();
        mCallPaymentCards = RestClient.getInstance().getApiClient().paymentCards(userId);
        mCallPaymentCards.enqueue(new Callback<PaymentCardsModelResponse>() {
            @Override
            public void onResponse(Call<PaymentCardsModelResponse> call, Response<PaymentCardsModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    paymentCards.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PaymentCardsModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return paymentCards;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
