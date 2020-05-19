package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.payment_card_default.PaymentCardDefaultModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCardDefaultRepository {

    private static PaymentCardDefaultRepository mInstance;
    private Call<PaymentCardDefaultModelResponse> mCallDefaultCard;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static PaymentCardDefaultRepository getInstance(){
        if (mInstance == null){
            mInstance = new PaymentCardDefaultRepository();
        }
        return mInstance;
    }

    public MutableLiveData<PaymentCardDefaultModelResponse> defaultCard(String cardId){
        mLoading.setValue(true);
        MutableLiveData<PaymentCardDefaultModelResponse> result = new MutableLiveData<>();
        mCallDefaultCard = RestClient.getInstance().getApiClient().defaultPaymentCard(cardId);
        mCallDefaultCard.enqueue(new Callback<PaymentCardDefaultModelResponse>() {
            @Override
            public void onResponse(Call<PaymentCardDefaultModelResponse> call, Response<PaymentCardDefaultModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PaymentCardDefaultModelResponse> call, Throwable t) {
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
