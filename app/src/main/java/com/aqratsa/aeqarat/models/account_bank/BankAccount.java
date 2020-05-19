package com.aqratsa.aeqarat.models.account_bank;

import com.google.gson.annotations.SerializedName;

public class BankAccount {

    @SerializedName("insurance")
    private String mInsuranceAmount;
    @SerializedName("bank_account")
    private String mAccountNumber;
    @SerializedName("name")
    private String mBankName;
    @SerializedName("bayan_number")
    private String mIBAN;

    public String getInsuranceAmount ()
    {
        return mInsuranceAmount;
    }

    public String getAccountNumber ()
    {
        return mAccountNumber;
    }

    public String getBankName() {
        return mBankName;
    }

    public String getIBAN() {
        return mIBAN;
    }
}
