package com.aqratsa.aeqarat.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.DocumentsAdapter;
import com.aqratsa.aeqarat.models.info_user.Documents;
import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.utils.FilePath;
import com.aqratsa.aeqarat.utils.ProgressRequestBody;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.interfaces.DeleteClickHandler;
import com.aqratsa.aeqarat.utils.interfaces.DownloadClickHandler;
import com.aqratsa.aeqarat.viewmodels.DocumentAddViewModel;
import com.aqratsa.aeqarat.viewmodels.DocumentDeleteViewModel;
import com.aqratsa.aeqarat.viewmodels.DownloadViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsAddFragment extends Fragment {

    private static final String TAG = DocumentsAddFragment.class.getSimpleName();

    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.addNewDocument)
    Button mAddNewDocument;
    @BindView(R.id.noDocuments)
    TextView mNoDocuments;

    private static final int TAKE_PHOTO_REQUEST_PERMISSION = 9008;
    private static final int REQUEST_PHOTO_CAMERA = 8007;
    private static final int REQUEST_PHOTO_GALLERY = 8008;
    private static final int DOWNLOAD_REQUEST_PERMISSION = 6006;
    private DocumentAddViewModel mViewModelAddDocument;
    private DocumentDeleteViewModel mViewModelDeleteDocument;
    private InfoUserViewModel mViewModelUserInfo;
    private DownloadViewModel mViewModelDownload;
    BottomSheetDialog mDialogSelectImage;
    private File mFile;
    private Uri mUri;
    private Toast mToast;
    private String mUserId;
    private RequestBody mRequestBodyUserId;
    private DocumentsAdapter mAdapter;
    private ArrayList<Documents> mListDocuments = new ArrayList<>();
    private int mPosition;
    private String mLocale;

    public DocumentsAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_documents, container, false);
        ButterKnife.bind(this, view);

        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar"))
            view.setRotation(-180);

        mViewModelAddDocument = ViewModelProviders.of(this).get(DocumentAddViewModel.class);
        mViewModelUserInfo = ViewModelProviders.of(getActivity()).get(InfoUserViewModel.class);
        mViewModelDeleteDocument = ViewModelProviders.of(this).get(DocumentDeleteViewModel.class);
        mViewModelDownload = ViewModelProviders.of(this).get(DownloadViewModel.class);

        mDialogSelectImage = new BottomSheetDialog(getActivity());
        mDialogSelectImage.setContentView(R.layout.dialog_select_image);

        GridLayoutManager layoutManager =
                new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mUserId = SharedPrefUtil.getInstance(getActivity()).read(USER_ID, null);
        if (mUserId == null){
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.getUser().observe(this, loginModelResponse -> {
                mUserId = loginModelResponse.getUser().getId();
                getDocuments();
            });
        } else {
            getDocuments();
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == TAKE_PHOTO_REQUEST_PERMISSION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getContext(), getString(R.string.permission_denied), Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
            }
            takeImage();
        } else if (requestCode == DOWNLOAD_REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModelDownload.download("akar" + mListDocuments.get(mPosition).getPhoto());
                mViewModelDownload.getFile().observe(DocumentsAddFragment.this, responseBody -> new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        writeResponseBodyToDisk(responseBody, mListDocuments.get(mPosition).getTitle());
                        return null;
                    }
                }.execute());
                mViewModelDownload.isLoading().observe(DocumentsAddFragment.this, loading -> {
                    if (loading) {
                        mProgress.setVisibility(View.VISIBLE);
                    } else {
                        mProgress.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CAMERA && getContext() != null && resultCode == RESULT_OK && mFile != null) {
            if (mFile.length() / 1024 > 5000){
                mToast = Toast.makeText(getActivity(), R.string.too_large_image, Toast.LENGTH_SHORT);
                mToast.show();
                return;
            }
            uploadImage();

        } else if (requestCode == REQUEST_PHOTO_GALLERY && getContext() != null && resultCode == RESULT_OK) {
            mUri = data.getData();
            String selectedFilePath = FilePath.getPath(getContext(), mUri);
            if (selectedFilePath != null) {
                mFile = new File(selectedFilePath);
                if (mFile.length() / 1024 > 5000){
                    mToast = Toast.makeText(getActivity(), R.string.too_large_image, Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
                uploadImage();
            }
        }
    }

    @OnClick(R.id.addNewDocument)
    public void addNewDocument() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            takeImage();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_REQUEST_PERMISSION);
        }
    }

    private void takeImage() {
        Window dialog = mDialogSelectImage.getWindow();
        TextView camera = dialog.findViewById(R.id.camera);
        TextView gallery = dialog.findViewById(R.id.gallery);
        ImageView close = dialog.findViewById(R.id.close);
        camera.setOnClickListener(v -> takeCameraImage());
        gallery.setOnClickListener(v -> takeGalleryImage());
        close.setOnClickListener(v -> mDialogSelectImage.dismiss());
        mDialogSelectImage.show();
    }

    private void takeCameraImage() {
        mDialogSelectImage.dismiss();
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getContext() != null && captureImageIntent.resolveActivity(getContext().getPackageManager()) != null) {
            try {
                mFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (mFile != null) {
                mUri = FileProvider.getUriForFile(getContext(),
                        "com.aqratsa.aeqarat.fileprovider",
                        mFile);

                captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(captureImageIntent, REQUEST_PHOTO_CAMERA);
            }
        }
    }

    private void takeGalleryImage() {
        mDialogSelectImage.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_photo)), REQUEST_PHOTO_GALLERY);
    }

    private File createImageFile() throws IOException {
        if (getContext() != null) {
            String timeStamp = String.valueOf(System.currentTimeMillis());
            String imageFileName = "JPEG_" + timeStamp;
            File storageDirectory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDirectory
            );

            return image;
        }
        return null;
    }

    private MultipartBody.Part prepareFilePart(String partName) {
        RequestBody requestFile = new ProgressRequestBody(getContext(), mFile, (progressInPercent, totalBytes) -> getActivity().runOnUiThread(() -> {
            mAddNewDocument.setEnabled(false);
            if (progressInPercent == 100) {
                mAddNewDocument.setEnabled(true);
                mAddNewDocument.setText(R.string.add_new_document);
            } else {
                mAddNewDocument.setText(progressInPercent + " %");
            }
        }));

        return MultipartBody.Part.createFormData(partName, mFile.getName(), requestFile);
    }

    private void getDocuments() {
        mAdapter = new DocumentsAdapter(getContext(), mListDocuments, new DeleteClickHandler() {
            @Override
            public void onClick(int position) {
                mViewModelDeleteDocument.deleteDocument(mListDocuments.get(position).getId());
                mViewModelDeleteDocument.getResult().observe(DocumentsAddFragment.this, deleteDocumentModelResponse -> {
                    if (deleteDocumentModelResponse.getKey().equals(SUCCESS)) {
                        mListDocuments.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                mViewModelDeleteDocument.isLoading().observe(DocumentsAddFragment.this, loading -> {
                    if (loading) {
                        mProgress.setVisibility(View.VISIBLE);
                    } else {
                        mProgress.setVisibility(View.GONE);
                    }
                });
            }
        }, new DownloadClickHandler() {
            @Override
            public void onClick(int position) {
                mPosition = position;
                if (ContextCompat.checkSelfPermission(DocumentsAddFragment.this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    mViewModelDownload.download(mListDocuments.get(position).getPhoto());
                    mViewModelDownload.getFile().observe(DocumentsAddFragment.this, responseBody -> new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            writeResponseBodyToDisk(responseBody, mListDocuments.get(position).getTitle());
                            return null;
                        }
                    }.execute());
                    mViewModelDownload.isLoading().observe(DocumentsAddFragment.this, loading -> {
                        if (loading) {
                            mProgress.setVisibility(View.VISIBLE);
                        } else {
                            mProgress.setVisibility(View.GONE);
                        }
                    });

                } else {
                    DocumentsAddFragment.this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_REQUEST_PERMISSION);
                }
            }
        });
        mViewModelUserInfo.userInfo(mUserId);
        mViewModelUserInfo.getUserInfo().observe(this, userInfoModelResponse -> {
            if (userInfoModelResponse.getKey().equals(SUCCESS)) {
                mListDocuments.clear();
                mListDocuments.addAll(Arrays.asList(userInfoModelResponse.getUser().getDocuments()));
                mAdapter.swapData(mListDocuments);
                mRecyclerView.setAdapter(mAdapter);
                if (mListDocuments.size() < 1) {
                    mNoDocuments.setVisibility(View.VISIBLE);
                } else {
                    mNoDocuments.setVisibility(View.GONE);
                }
            }
        });

        mViewModelUserInfo.isLoading().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });

        mViewModelUserInfo.isDeleting().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    // Download File
    private boolean writeResponseBodyToDisk(ResponseBody body, String title) {
        try {

            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), title);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mToast != null)
                            mToast.cancel();
                        mToast = Toast.makeText(getContext(), R.string.file_saved_in_downloads, Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                });

                return true;
            } catch (IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mToast != null)
                            mToast.cancel();
                        mToast = Toast.makeText(getContext(), R.string.download_failed, Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                });
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getContext(), R.string.download_failed, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
            return false;
        }
    }

    private void uploadImage(){
        mProgress.setVisibility(View.VISIBLE);
        mNoDocuments.setVisibility(View.INVISIBLE);
        mAddNewDocument.setEnabled(false);
        mAddNewDocument.setText(R.string.adjusting_image);
        mRequestBodyUserId = RequestBody.create(MultipartBody.FORM, mUserId);
        AsyncTask.execute(() -> {
            Bitmap bitmap = BitmapFactory.decodeFile(mFile.getPath());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(mFile);
                bitmap.compress(Bitmap.CompressFormat.WEBP, 77, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            MultipartBody.Part partFile = prepareFilePart("image");
            getActivity().runOnUiThread(() -> {
                mViewModelAddDocument.addDocument(partFile, mRequestBodyUserId);
                mViewModelAddDocument.newDocument().observe(DocumentsAddFragment.this, addDocumentModelResponse -> {
                    if (addDocumentModelResponse.getKey().equals(SUCCESS)) {
                        getDocuments();
                        mProgress.setVisibility(View.INVISIBLE);
                        mAddNewDocument.setEnabled(true);
                        mAddNewDocument.setText(R.string.add_new_document);
                    } else {
                        mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                        mToast.show();

                    }
                });
                mViewModelAddDocument.failure().observe(this, failure -> {
                    if (failure){
                        if (mToast != null)
                            mToast.cancel();
                        mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                });
            });
        });
    }

    public static DocumentsAddFragment newInstance() {
        return new DocumentsAddFragment();
    }
}
