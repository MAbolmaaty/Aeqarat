package com.aqratsa.aeqarat.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.info_user.UserInfoModelResponse;
import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.utils.FilePath;
import com.aqratsa.aeqarat.utils.FileUtils;
import com.aqratsa.aeqarat.utils.ProgressRequestBody;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.InfoUpdateViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hbb20.CountryCodePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.aqratsa.aeqarat.utils.Constants.ADDRESS;
import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;
import static com.aqratsa.aeqarat.utils.Constants.BIRTH_DATE;
import static com.aqratsa.aeqarat.utils.Constants.EMAIL;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.PHONE_CODE;
import static com.aqratsa.aeqarat.utils.Constants.PHONE_NUMBER;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USERNAME;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;
import static com.aqratsa.aeqarat.utils.Constants.USER_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoUserFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = InfoUserFragment.class.getSimpleName();

    @BindView(R.id.view_userImage)
    View mViewUserImage;
    @BindView(R.id.userImage)
    CircleImageView mUserImage;
    @BindView(R.id.shadow)
    ImageView mShadow;
    @BindView(R.id.camera)
    ImageView mCamera;
    @BindView(R.id.editText_username)
    EditText mUsername;
    @BindView(R.id.textView_birthDate)
    TextView mBirthDate;
    @BindView(R.id.calendar)
    ImageView mCalendar;
    @BindView(R.id.editText_address)
    EditText mAddress;
    @BindView(R.id.editText_postcode)
    EditText mPostcode;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.countryCodePicker)
    CountryCodePicker mCountryCodePicker;
    @BindView(R.id.editText_phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.save)
    Button mSave;
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.percentage)
    TextView mPercentage;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    private boolean mUpload;
    BottomSheetDialog mDialogSelectImage;
    private AlertDialog mDialogConfirmPassword;
    private File mFile;
    private Uri mUri;
    private Toast mToast;
    private String mNewBirthDate;
    private InfoUpdateViewModel mViewModelUpdateInfo;
    private String mUserId;
    private String mLocale;
    private static final int REQUEST_PHOTO_CAMERA = 8007;
    private static final int REQUEST_PHOTO_GALLERY = 8008;
    private static final int TAKE_PHOTO_REQUEST_PERMISSION = 9008;
    private MultipartBody.Part mPartFile;
    private InfoUserViewModel mViewModelInfoUser;

    public InfoUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar"))
            view.setRotation(-180);

        mDialogSelectImage = new BottomSheetDialog(getActivity());
        mDialogSelectImage.setContentView(R.layout.dialog_select_image);

        mViewModelUpdateInfo = ViewModelProviders.of(getActivity()).get(InfoUpdateViewModel.class);
        mViewModelInfoUser = ViewModelProviders.of(getActivity()).get(InfoUserViewModel.class);

        mUserId = SharedPrefUtil.getInstance(getActivity()).read(USER_ID, null);
        if (mUserId == null) {
            loadUserInfo();
        } else {
            loadUserInfo(getContext());
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
            takePhoto();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CAMERA && getContext() != null && resultCode == RESULT_OK) {
            Glide.with(getContext()).load(mFile).into(mUserImage);
            mUserImage.setVisibility(View.VISIBLE);
            mCamera.setVisibility(View.INVISIBLE);
            mUpload = false;

        } else if (requestCode == REQUEST_PHOTO_GALLERY && getContext() != null && resultCode == RESULT_OK) {
            mUri = data.getData();
            String selectedFilePath = FilePath.getPath(getContext(), mUri);
            if (selectedFilePath != null) {
                mFile = new File(selectedFilePath);
                Glide.with(getContext()).load(mFile).into(mUserImage);
                mUserImage.setVisibility(View.VISIBLE);
                mCamera.setVisibility(View.INVISIBLE);
                mUpload = false;
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mNewBirthDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        mBirthDate.setText(mNewBirthDate);
    }

    @OnClick({R.id.userImage, R.id.camera})
    public void clickOnImage() {
        if (mUpload) {
            if (getContext() != null) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_REQUEST_PERMISSION);
                }
            }
        } else {
            mShadow.setVisibility(View.VISIBLE);
            mDelete.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.constraintLayout)
    public void hideDelete() {
        mShadow.setVisibility(View.GONE);
        mDelete.setVisibility(View.GONE);
    }

    @OnClick(R.id.delete)
    public void delete() {
        mUserImage.setVisibility(View.INVISIBLE);
        mUpload = true;
        mShadow.setVisibility(View.INVISIBLE);
        mDelete.setVisibility(View.INVISIBLE);
        mCamera.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.textView_birthDate)
    public void pickBirthDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.show(getActivity().getSupportFragmentManager(), "DatePickerDialog");
    }

    @OnClick(R.id.save)
    public void save() {
        if (mToast != null)
            mToast.cancel();

        String newUsername = mUsername.getText().toString();
        mNewBirthDate = mBirthDate.getText().toString();
        String newAddress = mAddress.getText().toString();
        String newEmail = mEmail.getText().toString();
        String newCountryCode = "+" + mCountryCodePicker.getSelectedCountryCode();
        String newPhoneNumber = mPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(newUsername)) {
            newUsername = SharedPrefUtil.getInstance(getContext()).read(USERNAME, "");
        }

        if (TextUtils.isEmpty(newAddress)) {
            newAddress = SharedPrefUtil.getInstance(getContext()).read(ADDRESS, "");
        }

        if (TextUtils.isEmpty(newEmail)) {
            newEmail = SharedPrefUtil.getInstance(getContext()).read(EMAIL, "");
        }

        if (TextUtils.isEmpty(newPhoneNumber)) {
            newPhoneNumber = SharedPrefUtil.getInstance(getContext()).read(PHONE_NUMBER, "");
        }

        RequestBody userIdRequest = RequestBody.create(MultipartBody.FORM, mUserId),
                usernameRequest = RequestBody.create(MultipartBody.FORM, newUsername),
                birthDateRequest = RequestBody.create(MultipartBody.FORM, mNewBirthDate),
                addressRequest = RequestBody.create(MultipartBody.FORM, newAddress),
                emailRequest = RequestBody.create(MultipartBody.FORM, newEmail),
                countryCodeRequest = RequestBody.create(MultipartBody.FORM, newCountryCode),
                phoneNumberRequest = RequestBody.create(MultipartBody.FORM, newPhoneNumber),
                localeRequest = RequestBody.create(MultipartBody.FORM, mLocale);
        mProgress.setVisibility(View.VISIBLE);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
        AsyncTask.execute(() -> {
            if (mFile != null) {
                mUri = FileUtils.getUri(mFile);
                Bitmap bitmap = BitmapFactory.decodeFile(mFile.getPath());

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(mFile);
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 77, fileOutputStream);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPartFile = prepareFilePart("photo");
            }
            getActivity().runOnUiThread(() -> {
                mViewModelUpdateInfo.updateInfo(userIdRequest, usernameRequest, emailRequest, phoneNumberRequest,
                        countryCodeRequest, birthDateRequest, addressRequest, localeRequest, mPartFile);
                mViewModelUpdateInfo.getResult().observe(InfoUserFragment.this, updateInfoModelResponse -> {
                    if (updateInfoModelResponse.getKey().equals(SUCCESS)) {
                        InfoUpdateViewModel viewModelInfoUpdate = ViewModelProviders.of(getActivity()).get(InfoUpdateViewModel.class);
                        mToast = Toast.makeText(getActivity(), updateInfoModelResponse.getMessage(), Toast.LENGTH_SHORT);
                        mToast.show();
                        String userId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
                        if (userId != null) {
                            SharedPrefUtil.getInstance(getContext()).write(USER_IMAGE, updateInfoModelResponse.getResult().getPhoto());
                            SharedPrefUtil.getInstance(getContext()).write(USERNAME, updateInfoModelResponse.getResult().getName());
                            SharedPrefUtil.getInstance(getContext()).write(BIRTH_DATE, updateInfoModelResponse.getResult().getBirthday());
                            SharedPrefUtil.getInstance(getContext()).write(ADDRESS, updateInfoModelResponse.getResult().getArea());
                            SharedPrefUtil.getInstance(getContext()).write(EMAIL, updateInfoModelResponse.getResult().getEmail());
                            SharedPrefUtil.getInstance(getContext()).write(PHONE_NUMBER, updateInfoModelResponse.getResult().getPhone());
                            SharedPrefUtil.getInstance(getContext()).write(PHONE_CODE, updateInfoModelResponse.getResult().getCode());
                            viewModelInfoUpdate.infoUpdated(true);
                        } else {
                            mViewModelInfoUser.userInfo(mUserId);
                            mViewModelInfoUser.getUserInfo().observe(InfoUserFragment.this, userInfoModelResponse -> {
                                if (userInfoModelResponse.getKey().equals(SUCCESS)){
                                    viewModelInfoUpdate.infoUpdated(true);
                                }
                            });
                            mViewModelInfoUser.isLoading().observe(InfoUserFragment.this, loading -> {
                                if (loading){
                                    mProgress.setVisibility(View.VISIBLE);
                                } else {
                                    mProgress.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    } else {
                        mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                });
                mViewModelUpdateInfo.isLoading().observe(InfoUserFragment.this, loading -> {
                    if (loading) {
                        if (mFile != null) {
                            mProgressBar.setVisibility(View.VISIBLE);
                            mPercentage.setVisibility(View.VISIBLE);
                        }
                        mProgress.setVisibility(View.VISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        mPercentage.setVisibility(View.GONE);
                        mProgress.setVisibility(View.GONE);
                    }
                });
                mViewModelUpdateInfo.failure().observe(InfoUserFragment.this, failure -> {
                    if (failure) {
                        if (mToast != null)
                            mToast.cancel();
                        mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                });
            });
        });

    }

    public static InfoUserFragment newInstance() {
        return new InfoUserFragment();
    }

    private void loadUserInfo(Context context) {
        String userImage = SharedPrefUtil.getInstance(context).read(USER_IMAGE, null);
        String username = SharedPrefUtil.getInstance(context).read(USERNAME, "");
        String birthDate = SharedPrefUtil.getInstance(context).read(BIRTH_DATE, "");
        String address = SharedPrefUtil.getInstance(context).read(ADDRESS, "");
        String email = SharedPrefUtil.getInstance(context).read(EMAIL, "");
        String phoneCode = SharedPrefUtil.getInstance(context).read(PHONE_CODE, "");
        String phoneNumber = SharedPrefUtil.getInstance(context).read(PHONE_NUMBER, "");

        if (userImage != null && !TextUtils.isEmpty(userImage)) {
            mUserImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(BASE_URL + userImage).into(mUserImage);
        } else {
            mUserImage.setVisibility(View.INVISIBLE);
            mCamera.setVisibility(View.VISIBLE);
        }
        mUsername.setText(username);
        mBirthDate.setText(birthDate);
        mAddress.setText(address);
        mEmail.setText(email);
        if (phoneCode != null && phoneCode.length() > 0)
            mCountryCodePicker.setCountryForPhoneCode(Integer.parseInt(phoneCode));
        mPhoneNumber.setText(phoneNumber);
    }

    private void loadUserInfo() {
        LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        viewModelLogin.getUser().observe(this, loginModelResponse -> {
            mUserId = loginModelResponse.getUser().getId();
            mViewModelInfoUser.userInfo(mUserId);
            mViewModelInfoUser.getUserInfo().observe(InfoUserFragment.this, new Observer<UserInfoModelResponse>() {
                @Override
                public void onChanged(UserInfoModelResponse userInfoModelResponse) {
                    String userImage = userInfoModelResponse.getUser().getPhoto();
                    String username = userInfoModelResponse.getUser().getName();
                    String birthDate = userInfoModelResponse.getUser().getBirthday();
                    String address = userInfoModelResponse.getUser().getAddress();
                    String email = userInfoModelResponse.getUser().getEmail();
                    String phoneCode = userInfoModelResponse.getUser().getCode();
                    String phoneNumber = userInfoModelResponse.getUser().getPhone();

                    if (userImage != null && !TextUtils.isEmpty(userImage)) {
                        mUserImage.setVisibility(View.VISIBLE);
                        Glide.with(InfoUserFragment.this).load(BASE_URL + userImage).into(mUserImage);
                    } else {
                        mUserImage.setVisibility(View.INVISIBLE);
                        mCamera.setVisibility(View.VISIBLE);
                    }

                    mUsername.setText(username);
                    mBirthDate.setText(birthDate);
                    mAddress.setText(address);
                    mEmail.setText(email);
                    if (phoneCode != null && phoneCode.length() > 0)
                        mCountryCodePicker.setCountryForPhoneCode(Integer.parseInt(phoneCode));
                    mPhoneNumber.setText(phoneNumber);
                }
            });

            mViewModelInfoUser.isLoading().observe(InfoUserFragment.this, loading -> {
                if (loading){
                    mProgress.setVisibility(View.VISIBLE);
                } else {
                    mProgress.setVisibility(View.INVISIBLE);
                }
            });
        });
    }

    private void takePhoto() {
        Window dialog = mDialogSelectImage.getWindow();
        TextView camera = dialog.findViewById(R.id.camera);
        TextView gallery = dialog.findViewById(R.id.gallery);
        ImageView close = dialog.findViewById(R.id.close);
        camera.setOnClickListener(v -> takeCameraPhoto());
        gallery.setOnClickListener(v -> takeGalleryPhoto());
        close.setOnClickListener(v -> mDialogSelectImage.dismiss());
        mDialogSelectImage.show();
    }

    private void takeCameraPhoto() {
        mDialogSelectImage.dismiss();
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getContext() != null && captureImageIntent.resolveActivity(getContext().getPackageManager()) != null) {
            try {
                mFile = createPhotoFile();
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

    private void takeGalleryPhoto() {
        mDialogSelectImage.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_photo)), REQUEST_PHOTO_GALLERY);
    }

    private File createPhotoFile() throws IOException {
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
            mProgressBar.setProgress(progressInPercent);
            if (progressInPercent == 100) {
                mProgressBar.setVisibility(View.GONE);
                mPercentage.setVisibility(View.GONE);
            } else {
                mPercentage.setText(progressInPercent + " %");
            }
        }));

        return MultipartBody.Part.createFormData(partName, mFile.getName(), requestFile);
    }
}
