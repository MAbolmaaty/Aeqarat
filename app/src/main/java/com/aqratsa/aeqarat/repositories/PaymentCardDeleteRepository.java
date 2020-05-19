package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.payment_card_delete.PaymentCardDeleteModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCardDeleteRepository {

    private static PaymentCardDeleteRepository mInstance;
    private Call<PaymentCardDeleteModelResponse> mCallDeleteCard;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static PaymentCardDeleteRepository getInstance(){
        if (mInstance == null){
            mInstance = new PaymentCardDeleteRepository();
        }
        return mInstance;
    }

    public MutableLiveData<PaymentCardDeleteModelResponse> deleteCard(String cardId){
        mLoading.setValue(true);
        MutableLiveData<PaymentCardDeleteModelResponse> result = new MutableLiveData<>();
        mCallDeleteCard = RestClient.getInstance().getApiClient().deletePaymentCard(cardId);
        mCallDeleteCard.enqueue(new Callback<PaymentCardDeleteModelResponse>() {
            @Override
            public void onResponse(Call<PaymentCardDeleteModelResponse> call, Response<PaymentCardDeleteModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PaymentCardDeleteModelResponse> call, Throwable t) {
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
