package com.aqratsa.aeqarat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aqratsa.aeqarat.models.document_delete.DeleteDocumentModelResponse;
import com.aqratsa.aeqarat.repositories.DocumentDeleteRepository;

public class DocumentDeleteViewModel extends ViewModel {

    private MutableLiveData<DeleteDocumentModelResponse> mResult;
    private MutableLiveData<Boolean> mLoading;
    private MutableLiveData<Boolean> mFailure;

    public void deleteDocument(String document){
        DocumentDeleteRepository repositoryDeleteDocument = DocumentDeleteRepository.getInstance();
        mResult = repositoryDeleteDocument.deleteDocument(document);
        mLoading = repositoryDeleteDocument.loading();
        mFailure = repositoryDeleteDocument.failure();
    }

    public LiveData<DeleteDocumentModelResponse> getResult(){
        return mResult;
    }

    public LiveData<Boolean> isLoading(){
        return mLoading;
    }

    public LiveData<Boolean> failure(){return mFailure;}
}
