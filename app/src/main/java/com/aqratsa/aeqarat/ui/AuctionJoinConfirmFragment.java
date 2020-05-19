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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
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

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.FilePath;
import com.aqratsa.aeqarat.utils.ProgressRequestBody;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.AuctionJoinViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionJoinConfirmFragment extends Fragment {

    @BindView(R.id.view_file)
    View mViewFile;
    @BindView(R.id.view_info)
    View mViewInfo;
    @BindView(R.id.file)
    ImageView mReceiptImage;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;
    @BindView(R.id.fileName)
    TextView mFileName;
    @BindView(R.id.downloadFile)
    ImageView mDownloadFile;
    @BindView(R.id.deleteFile)
    ImageView mDeleteFile;
    @BindView(R.id.result)
    TextView mResult;
    @BindView(R.id.send)
    Button mSend;
    @BindView(R.id.frame)
    View mFrame;
    @BindView(R.id.addFile)
    Button mAddFile;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.toolbarAction1)
    ImageView mBack;

    private static final int TAKE_PHOTO_REQUEST_PERMISSION = 7878;
    private static final String TAG = AuctionJoinConfirmFragment.class.getSimpleName();

    private Toast mToast;
    private File mFile;
    private Uri mUri;
    private static final int REQUEST_IMAGE_CAMERA = 8007;
    private static final int REQUEST_IMAGE_GALLERY = 8008;

    private BottomSheetDialog mDialogSelectImage;
    private AuctionJoinViewModel mViewModelAuctionJoin;
    private RealEstateViewModel mViewModelRealEstate;
    private String mUserId;

    public AuctionJoinConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auction_join_confirm, container, false);
        ButterKnife.bind(this, view);
        mTitle.setText(R.string.auction_join);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
        }
        mUserId = SharedPrefUtil.getInstance(getActivity()).read(USER_ID, null);
        if (mUserId == null) {
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.getUser().observe(this, loginModelResponse -> mUserId = loginModelResponse.getUser().getId());
        }

        mDialogSelectImage = new BottomSheetDialog(getActivity());
        mDialogSelectImage.setContentView(R.layout.dialog_select_image);

        mViewModelAuctionJoin = ViewModelProviders.of(this).get(AuctionJoinViewModel.class);
        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);

        mResult.setText(R.string.no_files);

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
                    mToast = Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
            }
            takeImage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Glide.with(getActivity()).load(mFile).into(mReceiptImage);
            mResult.setText("");
            fileVisibility(true);
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            mUri = data.getData();
            String selectedFilePath = FilePath.getPath(getActivity(), mUri);
            mFile = new File(selectedFilePath);
            Glide.with(getActivity()).load(mUri).into(mReceiptImage);
            mResult.setText("");
            fileVisibility(true);
        }
    }

    @OnClick(R.id.toolbarAction1)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.addFile)
    public void addFile() {
        if (mReceiptImage.getVisibility() == View.VISIBLE){
            if (mToast != null)
                mToast.cancel();
            mToast = Toast.makeText(getActivity(), R.string.delete_file, Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            takeImage();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_REQUEST_PERMISSION);
        }
    }

    @OnClick(R.id.deleteFile)
    public void delete() {
        fileDeleteAnimation(true);
        new Handler().postDelayed(() -> {
            fileVisibility(false);
            fileDeleteAnimation(false);
            mResult.setText(R.string.no_files);
        }, 468);

    }

    @OnClick(R.id.send)
    public void send(){
        if (mToast != null){
            mToast.cancel();
        }
        uploadImage();
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
                startActivityForResult(captureImageIntent, REQUEST_IMAGE_CAMERA);
            }
        }
    }

    private void takeGalleryImage() {
        mDialogSelectImage.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_photo)), REQUEST_IMAGE_GALLERY);
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

    private void fileVisibility(boolean visibility) {
        if (visibility) {
            mViewFile.setVisibility(View.VISIBLE);
            mViewInfo.setVisibility(View.VISIBLE);
            mReceiptImage.setVisibility(View.VISIBLE);
            mFileName.setVisibility(View.VISIBLE);
            mDownloadFile.setVisibility(View.VISIBLE);
            mDeleteFile.setVisibility(View.VISIBLE);
            mFrame.setVisibility(View.VISIBLE);
            mSend.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.darkGrey));
            mSend.setEnabled(true);
        } else {
            mViewFile.setVisibility(View.INVISIBLE);
            mViewInfo.setVisibility(View.INVISIBLE);
            mReceiptImage.setVisibility(View.INVISIBLE);
            mFileName.setVisibility(View.INVISIBLE);
            mDownloadFile.setVisibility(View.INVISIBLE);
            mFrame.setVisibility(View.INVISIBLE);
            mDeleteFile.setVisibility(View.INVISIBLE);
            mSend.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.lightGrey));
            mSend.setEnabled(false);
        }
    }

    private void fileDeleteAnimation(boolean delete) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (delete) {
            mViewFile.animate().translationX(displayMetrics.widthPixels);
            mViewInfo.animate().translationX(displayMetrics.widthPixels);
            mReceiptImage.animate().translationX(displayMetrics.widthPixels);
            mFileName.animate().translationX(displayMetrics.widthPixels);
            mFrame.animate().translationX(displayMetrics.widthPixels);
            mDownloadFile.animate().translationX(displayMetrics.widthPixels);
            mDeleteFile.animate().translationX(displayMetrics.widthPixels);
        } else {
            mViewFile.setTranslationX(0);
            mViewInfo.setTranslationX(0);
            mReceiptImage.setTranslationX(0);
            mFileName.setTranslationX(0);
            mDownloadFile.setTranslationX(0);
            mFrame.setTranslationX(0);
            mDeleteFile.setTranslationX(0);
        }
    }

    private MultipartBody.Part prepareFilePart(String partName) {

        RequestBody requestFile = new ProgressRequestBody(getContext(), mFile,
                (progressInPercent, totalBytes) -> getActivity().runOnUiThread(() -> {
            mAddFile.setEnabled(false);
            mDeleteFile.setEnabled(false);
            if (progressInPercent == 100) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mResult.setText(getString(R.string.uploading, String.valueOf(progressInPercent)));
            }
        }));

        return MultipartBody.Part.createFormData(partName, mFile.getName(), requestFile);
    }

    private void uploadImage(){
        mProgress.setVisibility(View.VISIBLE);
        mAddFile.setEnabled(false);
        mSend.setEnabled(false);
        AsyncTask.execute(() -> {
            Bitmap bitmap = BitmapFactory.decodeFile(mFile.getPath());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(mFile);
                bitmap.compress(Bitmap.CompressFormat.WEBP, 77, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MultipartBody.Part partFile = prepareFilePart("doc");
            RequestBody userIdRequest = RequestBody.create(MultipartBody.FORM, mUserId);
            getActivity().runOnUiThread(() -> mViewModelRealEstate.getRealEstateId().observe(AuctionJoinConfirmFragment.this, realEstateId -> {
                RequestBody realEstateIdRequest = RequestBody.create(MultipartBody.FORM, realEstateId);
                mViewModelAuctionJoin.joinAuction(partFile, userIdRequest, realEstateIdRequest);
                mViewModelAuctionJoin.getData().observe(AuctionJoinConfirmFragment.this, auctionJoinModelResponse -> {
                    if (auctionJoinModelResponse.getKey().equals(SUCCESS)){
                        mToast = Toast.makeText(getActivity(), auctionJoinModelResponse.getMessage(), Toast.LENGTH_SHORT);
                        mToast.show();
                        mProgress.setVisibility(View.INVISIBLE);
                        mAddFile.setEnabled(true);
                        mDeleteFile.setEnabled(true);
                        mResult.setText(R.string.review_file);
                    } else {
                        mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                        mToast.show();

                    }
                });
                mViewModelAuctionJoin.failure().observe(AuctionJoinConfirmFragment.this, failure -> {
                    if (failure){
                        if (mToast != null)
                            mToast.cancel();
                        mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                });
            }));

        });
    }
}
