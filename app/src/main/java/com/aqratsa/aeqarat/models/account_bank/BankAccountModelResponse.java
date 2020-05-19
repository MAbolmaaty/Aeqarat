package com.aqratsa.aeqarat.models.account_bank;

import com.google.gson.annotations.SerializedName;

public class BankAccountModelResponse {

    @SerializedName("data")
    private BankAccount mBankAccount;

    private String key;

    public BankAccount getBankAccount ()
    {
        return mBankAccount;
    }

    public String getKey ()
    {
        return key;
    }
}
