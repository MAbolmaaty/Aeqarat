package com.aqratsa.aeqarat.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.DocumentsAdapter;
import com.aqratsa.aeqarat.models.info_user.Documents;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.DocumentDeleteViewModel;
import com.aqratsa.aeqarat.viewmodels.DownloadViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;

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
import okhttp3.ResponseBody;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsFragment extends Fragment {

    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.noDocuments)
    TextView mNoDocuments;

    private DocumentsAdapter mAdapter;
    private ArrayList<Documents> mListDocuments = new ArrayList<>();
    private DocumentDeleteViewModel mViewModelDeleteDocument;
    private int mPosition;
    private DownloadViewModel mViewModelDownload;
    private Toast mToast;
    private static final int DOWNLOAD_REQUEST_PERMISSION = 6006;

    public DocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        ButterKnife.bind(this, view);

        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar"))
            view.setRotation(-180);

        InfoUserViewModel viewModelUserInfo = ViewModelProviders.of(getActivity()).get(InfoUserViewModel.class);
        mViewModelDeleteDocument = ViewModelProviders.of(this).get(DocumentDeleteViewModel.class);
        mViewModelDownload = ViewModelProviders.of(this).get(DownloadViewModel.class);

        mAdapter = new DocumentsAdapter(getContext(), mListDocuments, position -> {
            mViewModelDeleteDocument.deleteDocument(mListDocuments.get(position).getId());
            mViewModelDeleteDocument.getResult().observe(DocumentsFragment.this, deleteDocumentModelResponse -> {
                if (deleteDocumentModelResponse.getKey().equals(SUCCESS)) {
                    mListDocuments.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            });
            mViewModelDeleteDocument.isLoading().observe(DocumentsFragment.this, loading -> {
                if (loading) {
                    mProgress.setVisibility(View.VISIBLE);
                } else {
                    mProgress.setVisibility(View.GONE);
                }
            });
        }, position -> {
            mPosition = position;
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                mViewModelDownload.download("akar" + mListDocuments.get(position).getPhoto());
                mViewModelDownload.getFile().observe(DocumentsFragment.this, responseBody -> new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        writeResponseBodyToDisk(responseBody, mListDocuments.get(position).getTitle());
                        return null;
                    }
                }.execute());
                mViewModelDownload.isLoading().observe(DocumentsFragment.this, loading -> {
                    if (loading) {
                        mProgress.setVisibility(View.VISIBLE);
                    } else {
                        mProgress.setVisibility(View.GONE);
                    }
                });

            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_REQUEST_PERMISSION);
            }
        });

        GridLayoutManager layoutManager =
                new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        viewModelUserInfo.getUserInfo().observe(this, userInfoModelResponse -> {
            if (userInfoModelResponse.getKey().equals(SUCCESS)) {
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

        viewModelUserInfo.isLoading().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == DOWNLOAD_REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModelDownload.download("akar" + mListDocuments.get(mPosition).getPhoto());
                mViewModelDownload.getFile().observe(DocumentsFragment.this, responseBody -> new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        writeResponseBodyToDisk(responseBody, mListDocuments.get(mPosition).getTitle());
                        return null;
                    }
                }.execute());
                mViewModelDownload.isLoading().observe(DocumentsFragment.this, loading -> {
                    if (loading) {
                        mProgress.setVisibility(View.VISIBLE);
                    } else {
                        mProgress.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

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

    public static DocumentsFragment newInstance() {
        return new DocumentsFragment();
    }
}
