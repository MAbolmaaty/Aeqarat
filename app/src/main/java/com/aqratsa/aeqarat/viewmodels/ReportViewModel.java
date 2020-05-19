package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.report.response.ReportModelResponse;
import com.aqratsa.aeqarat.repositories.ReportRepository;

public class ReportViewModel extends ViewModel {

    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<ReportModelResponse> mResult;
    private MutableLiveData<Boolean> mFailure;

    public void report(String message, String userId, String realEstateId,
                       String locale, String status){
        ReportRepository repositoryReport = ReportRepository.getInstance();
        mResult = repositoryReport.report(message, userId, realEstateId,
                locale, status);
        mLoading = repositoryReport.loading();
        mFailure = repositoryReport.failure();
    }

    public LiveData<ReportModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
