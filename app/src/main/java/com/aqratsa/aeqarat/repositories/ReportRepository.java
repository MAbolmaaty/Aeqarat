package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.report.request.ReportModelRequest;
import com.aqratsa.aeqarat.models.report.response.ReportModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportRepository {

    private static final String TAG = ReportRepository.class.getSimpleName();

    private static ReportRepository instance;
    private Call<ReportModelResponse> mCallReport;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static ReportRepository getInstance(){
        if (instance == null){
            instance = new ReportRepository();
        }
        return instance;
    }

    public MutableLiveData<ReportModelResponse> report(String message, String userId, String realEstateId,
                                                       String locale, String status){
        mLoading.setValue(true);
        MutableLiveData<ReportModelResponse> result = new MutableLiveData<>();
        mCallReport = RestClient.getInstance().getApiClient().report(new ReportModelRequest(message,
                userId, realEstateId, locale, status));
        mCallReport.enqueue(new Callback<ReportModelResponse>() {
            @Override
            public void onResponse(Call<ReportModelResponse> call, Response<ReportModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ReportModelResponse> call, Throwable t) {
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
