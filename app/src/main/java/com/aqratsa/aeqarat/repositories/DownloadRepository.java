package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.utils.web_service.RestClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadRepository {

    private static DownloadRepository instance;
    private Call<ResponseBody> mCallDownload;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static DownloadRepository getInstance() {
        if (instance == null) {
            instance = new DownloadRepository();
        }
        return instance;
    }

    public MutableLiveData<ResponseBody> download(String url) {
        mLoading.setValue(true);
        MutableLiveData<ResponseBody> file = new MutableLiveData<>();
        mCallDownload = RestClient.getInstance().getApiClient().download(url);
        mCallDownload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    file.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return file;
    }

    public MutableLiveData<Boolean> loading() {
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
