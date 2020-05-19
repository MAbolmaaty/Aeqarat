package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.login.request.LoginModelRequest;
import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.models.register.RegisterModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();

    private static LoginRepository instance;
    private Call<LoginModelResponse> mCallLogin;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public MutableLiveData<LoginModelResponse> getUser(String email, String password, String locale,
                                                       String fcmToken, String deviceOS) {
        mLoading.setValue(true);
        MutableLiveData<LoginModelResponse> user = new MutableLiveData<>();
        mCallLogin = RestClient.getInstance().getApiClient().login(new LoginModelRequest(email,
                password, locale, fcmToken, deviceOS));
        mCallLogin.enqueue(new Callback<LoginModelResponse>() {
            @Override
            public void onResponse(Call<LoginModelResponse> call, Response<LoginModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    user.setValue(response.body());
                } else if (response.errorBody() != null){
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        String key = jsonObject.getString("key");
                        user.setValue(new LoginModelResponse(message, key));
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
            public void onFailure(Call<LoginModelResponse> call, Throwable t) {
                Log.d(TAG, "on failure");
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return user;
    }

    public MutableLiveData<Boolean> loading() {
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
