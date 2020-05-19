package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.request_maintenance.response.RequestMaintenanceModelResponse;
import com.aqratsa.aeqarat.repositories.RequestMaintenanceRepository;

public class RequestMaintenanceViewModel extends ViewModel {

    private RequestMaintenanceRepository mRepositoryMaintenance;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<RequestMaintenanceModelResponse> mResult;
    private MutableLiveData<Boolean> mFailure;

    public void maintenance(String realEstateId,
                            String userId, String type,
                            String description,
                            String locale){

        if (mRepositoryMaintenance != null)
            return;
        mRepositoryMaintenance = RequestMaintenanceRepository.getInstance();
        mResult = mRepositoryMaintenance.maintenance(realEstateId, userId, type,
                description, locale);
        mLoading = mRepositoryMaintenance.loading();
        mFailure = mRepositoryMaintenance.failure();
    }

    public LiveData<RequestMaintenanceModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
