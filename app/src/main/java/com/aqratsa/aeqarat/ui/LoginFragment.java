package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.login.response.User;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.progress;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.ADDRESS;
import static com.aqratsa.aeqarat.utils.Constants.BIRTH_DATE;
import static com.aqratsa.aeqarat.utils.Constants.EMAIL;
import static com.aqratsa.aeqarat.utils.Constants.FAILED;
import static com.aqratsa.aeqarat.utils.Constants.FORGET_PASSWORD;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.PHONE_CODE;
import static com.aqratsa.aeqarat.utils.Constants.PHONE_NUMBER;
import static com.aqratsa.aeqarat.utils.Constants.REGISTER;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USERNAME;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;
import static com.aqratsa.aeqarat.utils.Constants.USER_IMAGE;
import static com.aqratsa.aeqarat.utils.Constants.USER_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private LoginViewModel mViewModelLogin;

    @BindView(R.id.button_login)
    Button mButtonLogin;
    @BindView(R.id.email)
    EditText mEditTextEmail;
    @BindView(R.id.password)
    TextInputEditText mEditTextPassword;
    @BindView(R.id.skip)
    TextView mSkip;
    @BindView(R.id.remember)
    CheckBox mRemember;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.progressSkip)
    ProgressBar mProgressSkip;

    private String mFcmToken;
    private String mDeviceOS;
    private Toast mToast;
    private String mLocale;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        retrieveToken();
        mViewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        String userId = SharedPrefUtil.getInstance(getActivity()).read(USER_ID, null);
        if (userId != null) {
            mViewModelLogin.loggedIn(true);
        } else {
            mViewModelLogin.loggedIn(false);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
    }

    @OnClick(R.id.button_login)
    public void login() {
        if (mToast != null)
            mToast.cancel();
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            mToast = Toast.makeText(getActivity(), R.string.enter_email, Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            mToast = Toast.makeText(getActivity(), R.string.enter_password, Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        SoftKeyboard.dismissKeyboardInActivity(getContext());

        mViewModelLogin.login(email, password, mLocale, mFcmToken, mDeviceOS);
        mViewModelLogin.getUser().observe(this, loginModelResponse -> {
            if (loginModelResponse.getKey().equals(SUCCESS)) {
                if (mRemember.isChecked()) {
                    saveUser(loginModelResponse.getUser());
                }
                mViewModelLogin.loggedIn(true);
                mToast = Toast.makeText(getActivity(), loginModelResponse.getMessage(), Toast.LENGTH_SHORT);
                mToast.show();

            } else if (loginModelResponse.getKey().equals(FAILED)) {
                mToast = Toast.makeText(getActivity(), loginModelResponse.getMessage(), Toast.LENGTH_SHORT);
                mToast.show();
                mProgress.setVisibility(View.INVISIBLE);
            }
        });
        mViewModelLogin.isLoading().observe(this, loading -> {
            mProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
        });
        mViewModelLogin.failure().observe(this, failure -> {
            if (failure) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    @OnClick(R.id.skip)
    public void skip() {
        mProgressSkip.setVisibility(View.VISIBLE);
        mSkip.setTextColor(getResources().getColor(R.color.grey));
        loadFragment(getActivity().getSupportFragmentManager(), new HomeFragment(), false);
    }

    @OnClick(R.id.forgetPassword)
    public void forgetPassword() {
        loadFragment(getActivity().getSupportFragmentManager(),
                new EmailConfirmFragment(), true);
        mViewModelLogin.confirmEmailFor(FORGET_PASSWORD);
    }

    @OnClick(R.id.register)
    public void register() {
        loadFragment(getActivity().getSupportFragmentManager(),
                new EmailConfirmFragment(), true);
        mViewModelLogin.confirmEmailFor(REGISTER);
    }

    private void retrieveToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    mFcmToken = task.getResult().getToken();
                    Log.d(TAG, mFcmToken);
                    mDeviceOS = "android";
                });
    }

    private void saveUser(User user) {
        SharedPrefUtil.getInstance(getActivity()).write(USER_ID, user.getId());
        SharedPrefUtil.getInstance(getActivity()).write(USER_IMAGE, user.getPhoto());
        SharedPrefUtil.getInstance(getActivity()).write(USERNAME, user.getName());
        SharedPrefUtil.getInstance(getActivity()).write(USER_TYPE, user.getType());
        SharedPrefUtil.getInstance(getActivity()).write(BIRTH_DATE, user.getBirthday());
        SharedPrefUtil.getInstance(getActivity()).write(ADDRESS, user.getArea());
        SharedPrefUtil.getInstance(getActivity()).write(EMAIL, user.getEmail());
        SharedPrefUtil.getInstance(getActivity()).write(PHONE_NUMBER, user.getPhone());
        SharedPrefUtil.getInstance(getActivity()).write(PHONE_CODE, user.getCode());
    }
}
