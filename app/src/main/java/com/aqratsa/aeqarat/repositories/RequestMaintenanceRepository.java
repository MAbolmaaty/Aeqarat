package com.aqratsa.aeqarat.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.request_maintenance.request.RequestMaintenanceModelRequest;
import com.aqratsa.aeqarat.models.request_maintenance.response.RequestMaintenanceModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestMaintenanceRepository {

    private static final String TAG = RequestMaintenanceRepository.class.getSimpleName();
    private static RequestMaintenanceRepository mInstance;
    private Call<RequestMaintenanceModelResponse> mCallRequestMaintenance;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static RequestMaintenanceRepository getInstance(){
        if (mInstance == null){
            mInstance = new RequestMaintenanceRepository();
        }
        return mInstance;
    }

    public MutableLiveData<RequestMaintenanceModelResponse> maintenance(String realEstateId,
                                                                        String userId, String type,
                                                                        String description,
                                                                        String locale){
        mLoading.setValue(true);
        MutableLiveData<RequestMaintenanceModelResponse> result = new MutableLiveData<>();
        mCallRequestMaintenance = RestClient.getInstance().getApiClient()
                .maintenance(new RequestMaintenanceModelRequest(realEstateId, userId,
                        type, description, locale));
        mCallRequestMaintenance.enqueue(new Callback<RequestMaintenanceModelResponse>() {
            @Override
            public void onResponse(Call<RequestMaintenanceModelResponse> call, Response<RequestMaintenanceModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null) {
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestMaintenanceModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });
        return result;
    }

    public MutableLiveData<Boolean> loading() {
        return mLoading;
    }
    public MutableLiveData<Boolean> failure(){return mFailure;}
}
