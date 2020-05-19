package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.register.RegisterModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterRepository {

    private static final String TAG = RegisterRepository.class.getSimpleName();

    private static RegisterRepository mInstance;
    private Call<RegisterModelResponse> mCallRegister;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RegisterRepository getInstance() {
        if (mInstance == null) {
            mInstance = new RegisterRepository();
        }
        return mInstance;
    }

    public MutableLiveData<RegisterModelResponse> register(RequestBody username,
                                                           RequestBody email, RequestBody password,
                                                           RequestBody countryCode, RequestBody phoneNumber,
                                                           RequestBody locale,
                                                           MultipartBody.Part userImage,
                                                           RequestBody fcmToken,
                                                           RequestBody device) {

        mLoading.setValue(true);
        MutableLiveData<RegisterModelResponse> result = new MutableLiveData<>();
        mCallRegister = RestClient.getInstance().getApiClient().register(username,
                email, password, countryCode, phoneNumber, locale, userImage, fcmToken, device);
        mCallRegister.enqueue(new Callback<RegisterModelResponse>() {
            @Override
            public void onResponse(Call<RegisterModelResponse> call, Response<RegisterModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    result.setValue(response.body());
                } else if (response.errorBody() != null){
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        String key = jsonObject.getString("key");
                        result.setValue(new RegisterModelResponse(message, key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mFailure.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<RegisterModelResponse> call, Throwable t) {
                Log.d(TAG, "on failure");
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
