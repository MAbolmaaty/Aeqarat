package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.email_confirm.response.ConfirmEmailModelResponse;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.EmailConfirmViewModel;
import com.aqratsa.aeqarat.viewmodels.PasswordForgetViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.FORGET_PASSWORD;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REGISTER;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailConfirmFragment extends Fragment {

    private static final String TAG = EmailConfirmFragment.class.getSimpleName();

    @BindView(R.id.close)
    ImageView mClose;
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    private Toast mToast;
    private LoginViewModel mViewModelLogin;
    private EmailConfirmViewModel mViewModelConfirmEmail;
    private PasswordForgetViewModel mViewModelForgetPassword;
    private String mLocale;

    public EmailConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_email, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        mViewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        mViewModelConfirmEmail = ViewModelProviders.of(getActivity()).get(EmailConfirmViewModel.class);
        mViewModelForgetPassword = ViewModelProviders.of(getActivity()).get(PasswordForgetViewModel.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
    }

    @OnClick(R.id.close)
    public void close() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.confirm)
    public void confirm() {
        if (mToast != null)
            mToast.cancel();

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mToast = Toast.makeText(getContext(), R.string.enter_email, Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }
        SoftKeyboard.dismissKeyboardInActivity(getContext());
        mViewModelLogin.confirmFor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String confirmEmailFor) {
                if (confirmEmailFor.equals(FORGET_PASSWORD)){
                    forgetPassword(email, mLocale);
                } else if (confirmEmailFor.equals(REGISTER)){
                    confirmEmail(email, mLocale);
                }
            }
        });
    }

    private void forgetPassword(String email, String locale) {
        mViewModelForgetPassword = ViewModelProviders.of(getActivity()).get(PasswordForgetViewModel.class);
        mViewModelForgetPassword.forgetPassword(email, locale);
        mViewModelForgetPassword.getResult().observe(this, forgetPasswordModelResponse -> {
            if (forgetPasswordModelResponse.getKey().equals(SUCCESS)) {
                mToast = Toast.makeText(getContext(), forgetPasswordModelResponse.getResult(), Toast.LENGTH_SHORT);
                mToast.show();
                loadFragment(getActivity().getSupportFragmentManager(),
                        new CodeConfirmFragment(), false);
            } else if (forgetPasswordModelResponse.getResult() != null){
                mToast = Toast.makeText(getActivity(), forgetPasswordModelResponse.getResult(),
                        Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
        mViewModelForgetPassword.isLoading().observe(this, loading -> progress(loading));
        mViewModelForgetPassword.failure().observe(this, failure -> {
            if (failure){
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void confirmEmail(String email, String locale) {
        mViewModelConfirmEmail = ViewModelProviders.of(getActivity()).get(EmailConfirmViewModel.class);
        mViewModelConfirmEmail.confirmEmail(email, locale);
        mViewModelConfirmEmail.getResult().observe(this, confirmEmailModelResponse -> {
            if (confirmEmailModelResponse.getKey().equals(SUCCESS)) {
                mViewModelConfirmEmail.email(email);
                mToast = Toast.makeText(getContext(), confirmEmailModelResponse.getResult(), Toast.LENGTH_SHORT);
                mToast.show();
                loadFragment(getActivity().getSupportFragmentManager(),
                        new CodeConfirmFragment(), false);
            } else if (confirmEmailModelResponse.getResult() != null){
                mToast = Toast.makeText(getActivity(), confirmEmailModelResponse.getResult(),
                        Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
        mViewModelConfirmEmail.isLoading().observe(this, loading -> progress(loading));
        mViewModelConfirmEmail.failure().observe(this, failure -> {
            if (failure){
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void progress(boolean loading) {
        MainActivity.progress(loading, mProgress, mConfirm);
    }
}
