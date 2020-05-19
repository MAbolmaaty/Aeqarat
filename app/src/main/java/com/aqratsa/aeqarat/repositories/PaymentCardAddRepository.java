package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.payment_card_add.request.PaymentCardAddModelRequest;
import com.aqratsa.aeqarat.models.payment_card_add.response.PaymentCardAddModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCardAddRepository {

    private static PaymentCardAddRepository mInstance;
    private Call<PaymentCardAddModelResponse> mCallPaymentCardAdd;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();


    public static PaymentCardAddRepository getInstance(){
        if (mInstance == null){
            mInstance = new PaymentCardAddRepository();
        }
        return mInstance;
    }

    public MutableLiveData<PaymentCardAddModelResponse> addPaymentCard(String cardNumber,
                                                                       String expireDate,
                                                                       String userId,
                                                                       String type,
                                                                       String locale,
                                                                       String cvv,
                                                                       String status){
        mLoading.setValue(true);
        MutableLiveData<PaymentCardAddModelResponse> paymentCard = new MutableLiveData<>();
        mCallPaymentCardAdd = RestClient.getInstance().getApiClient()
                .addPaymentCard(new PaymentCardAddModelRequest(cardNumber, expireDate, userId, type,
                        locale, cvv, status));
        mCallPaymentCardAdd.enqueue(new Callback<PaymentCardAddModelResponse>() {
            @Override
            public void onResponse(Call<PaymentCardAddModelResponse> call, Response<PaymentCardAddModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null)
                    paymentCard.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PaymentCardAddModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return paymentCard;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
