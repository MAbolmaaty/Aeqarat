package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.info_user.UserInfoModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoUserRepository {

    private static final String TAG = InfoUserRepository.class.getSimpleName();

    private static InfoUserRepository instance;
    private Call<UserInfoModelResponse> mCallUserInfo;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static InfoUserRepository getInstance(){
        if (instance == null){
            instance = new InfoUserRepository();

        }
        return instance;
    }

    public MutableLiveData<UserInfoModelResponse> userInfo(String userId){
        mLoading.setValue(true);
        MutableLiveData<UserInfoModelResponse> userInfo = new MutableLiveData<>();
        mCallUserInfo = RestClient.getInstance().getApiClient().getUserInfo(userId);
        mCallUserInfo.enqueue(new Callback<UserInfoModelResponse>() {
            @Override
            public void onResponse(Call<UserInfoModelResponse> call, Response<UserInfoModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    userInfo.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserInfoModelResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return userInfo;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
