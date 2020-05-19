package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.account_bank.BankAccountModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountBankRepository {

    private static AccountBankRepository mInstance;
    private Call<BankAccountModelResponse> mCallBankAccount;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static AccountBankRepository getInstance(){
        if (mInstance == null){
            mInstance = new AccountBankRepository();
        }
        return mInstance;
    }

    public MutableLiveData<BankAccountModelResponse> getBankAccount(String realEstateId){
        mLoading.setValue(true);

        MutableLiveData<BankAccountModelResponse> bankAccount = new MutableLiveData<>();
        mCallBankAccount = RestClient.getInstance().getApiClient().getBankAccount(realEstateId);
        mCallBankAccount.enqueue(new Callback<BankAccountModelResponse>() {
            @Override
            public void onResponse(Call<BankAccountModelResponse> call, Response<BankAccountModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    bankAccount.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BankAccountModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });

        return bankAccount;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
