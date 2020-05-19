package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.contact_us.response.ContactUsModelResponse;
import com.aqratsa.aeqarat.repositories.ContactUsRepository;

public class ContactUsViewModel extends ViewModel {

    private MutableLiveData<ContactUsModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void contactUs(String username, String email, String code, String phoneNumber,
                          String message, String title, String locale){
        ContactUsRepository repositoryContactUs = ContactUsRepository.getInstance();
        mResult = repositoryContactUs.contactUs(username, email, code, phoneNumber, message, title,
                locale);
        mLoading = repositoryContactUs.loading();
        mFailure = repositoryContactUs.failure();
    }

    public LiveData<ContactUsModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
