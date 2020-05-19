package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.info_update.UpdateInfoModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoUpdateRepository {

    private static final String TAG = InfoUpdateRepository.class.getSimpleName();

    private static InfoUpdateRepository instance;
    private Call<UpdateInfoModelResponse> mCallUpdateInfo;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static InfoUpdateRepository getInstance(){
        if (instance == null){
            instance = new InfoUpdateRepository();
        }
        return instance;
    }

    public MutableLiveData<UpdateInfoModelResponse> updateInfo(RequestBody userId,
                                                               RequestBody username,
                                                               RequestBody email,
                                                               RequestBody phoneNumber,
                                                               RequestBody phoneCode,
                                                               RequestBody birthday,
                                                               RequestBody address,
                                                               RequestBody locale,
                                                               MultipartBody.Part profilePhoto){

        mLoading.setValue(true);
        MutableLiveData<UpdateInfoModelResponse> result = new MutableLiveData<>();
        mCallUpdateInfo = RestClient.getInstance().getApiClient().updateInfo(userId,
                username, email, phoneNumber, phoneCode, birthday, address, locale, profilePhoto);
        mCallUpdateInfo.enqueue(new Callback<UpdateInfoModelResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoModelResponse> call, Response<UpdateInfoModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateInfoModelResponse> call, Throwable t) {
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
