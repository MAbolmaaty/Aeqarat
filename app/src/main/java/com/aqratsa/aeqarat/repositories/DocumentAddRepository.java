package com.aqratsa.aeqarat.repositories;

import androidx.lifecycle.MutableLiveData;

import com.aqratsa.aeqarat.models.document_add.AddDocumentModelResponse;
import com.aqratsa.aeqarat.utils.web_service.RestClient;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentAddRepository {

    private static DocumentAddRepository instance;
    private Call<AddDocumentModelResponse> mCallAddDocument;
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFailure = new MutableLiveData<>();

    public static DocumentAddRepository getInstance(){
        if (instance == null){
            instance = new DocumentAddRepository();
        }
        return instance;
    }

    public MutableLiveData<AddDocumentModelResponse> addDocument(MultipartBody.Part document,
                                                                 RequestBody userId){
        mLoading.setValue(true);
        MutableLiveData<AddDocumentModelResponse> newDocument = new MutableLiveData<>();
        mCallAddDocument = RestClient.getInstance().getApiClient()
                .addDocumentation(document, userId);
        mCallAddDocument.enqueue(new Callback<AddDocumentModelResponse>() {
            @Override
            public void onResponse(Call<AddDocumentModelResponse> call, Response<AddDocumentModelResponse> response) {
                mLoading.setValue(false);
                mFailure.setValue(false);
                if (response.body() != null){
                    newDocument.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddDocumentModelResponse> call, Throwable t) {
                mLoading.setValue(false);
                mFailure.setValue(true);
            }
        });

        return newDocument;
    }

    public MutableLiveData<Boolean> loading(){
        return mLoading;
    }

    public MutableLiveData<Boolean> failure(){return mFailure;}
}
