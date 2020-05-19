package com.aqratsa.aeqarat.repositories;

import android.text.BoringLayout;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.contact_us.request.ContactUsModelRequest;
import com.aqratsa.aeqarat.models.contact_us.response.ContactUsModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsRepository {

    private static ContactUsRepository instance;
    private Call<ContactUsModelResponse> mCallContactUs;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static ContactUsRepository getInstance(){
        if (instance == null){
            instance = new ContactUsRepository();
        }
        return instance;
    }

    public MutableLiveData<ContactUsModelResponse> contactUs(String username, String email, String code, String phoneNumber,
                                                             String message, String title, String locale){

        mLoading.setValue(true);
        MutableLiveData<ContactUsModelResponse> result = new MutableLiveData<>();
        mCallContactUs = RestClient.getInstance().getApiClient().contactUs(new ContactUsModelRequest(username,
                email, code, phoneNumber, message, title, locale));
        mCallContactUs.enqueue(new Callback<ContactUsModelResponse>() {
            @Override
            public void onResponse(Call<ContactUsModelResponse> call, Response<ContactUsModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ContactUsModelResponse> call, Throwable t) {
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
