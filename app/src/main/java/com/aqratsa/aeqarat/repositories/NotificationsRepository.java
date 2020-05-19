package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.notifications.NotificationsModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsRepository {

    private static NotificationsRepository mInstance;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static NotificationsRepository getInstance(){
        if (mInstance == null){
            mInstance = new NotificationsRepository();
        }
        return mInstance;
    }

    public MutableLiveData<NotificationsModelResponse> getNotifications(String userId){

        mLoading.setValue(true);
        MutableLiveData<NotificationsModelResponse> notifications = new MutableLiveData<>();
        Call<NotificationsModelResponse> callNotifications = RestClient.getInstance().getApiClient().getNotifications(userId);
        callNotifications.enqueue(new Callback<NotificationsModelResponse>() {
            @Override
            public void onResponse(Call<NotificationsModelResponse> call, Response<NotificationsModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    notifications.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NotificationsModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return notifications;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
