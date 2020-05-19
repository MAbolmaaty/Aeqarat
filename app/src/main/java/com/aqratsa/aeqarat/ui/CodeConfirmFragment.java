package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
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
import static com.aqratsa.aeqarat.utils.Constants.FORGET_PASSWORD;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REGISTER;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeConfirmFragment extends Fragment {

    @BindView(R.id.digit1)
    EditText mDigit1;
    @BindView(R.id.digit2)
    EditText mDigit2;
    @BindView(R.id.digit3)
    EditText mDigit3;
    @BindView(R.id.digit4)
    EditText mDigit4;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.close)
    ImageView mClose;

    private Toast mToast;
    private LoginViewModel mViewModelLogin;
    private PasswordForgetViewModel mViewModelForgetPassword;
    private EmailConfirmViewModel mViewModelConfirmEmail;
    private String mLocale;

    public CodeConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_code, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        mViewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        mViewModelForgetPassword = ViewModelProviders.of(getActivity()).get(PasswordForgetViewModel.class);
        mViewModelConfirmEmail = ViewModelProviders.of(getActivity()).get(EmailConfirmViewModel.class);
        setEditTextsFocus();

        return view;
    }

    @OnClick(R.id.close)
    public void close() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.confirm)
    public void confirmCode() {

        if (mToast != null)
            mToast.cancel();

        if (mDigit1.length() < 1 || mDigit2.length() < 1 || mDigit3.length() < 1 || mDigit4.length() < 1) {
            mToast = Toast.makeText(getContext(), R.string.invalid_code, Toast.LENGTH_LONG);
            mToast.show();
            return;
        }

        String confirmationCode = mDigit1.getText().toString() + mDigit2.getText().toString()
                + mDigit3.getText().toString() + mDigit4.getText().toString();

        mProgress.setVisibility(View.VISIBLE);

        SoftKeyboard.dismissKeyboardInActivity(getContext());

        mViewModelLogin.confirmFor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String confirmCodeFor) {
                if (confirmCodeFor.equals(FORGET_PASSWORD)){
                    resetPassword(confirmationCode);
                } else if (confirmCodeFor.equals(REGISTER)){
                    register(confirmationCode);
                }
            }
        });
    }

    @OnClick(R.id.sendAgain)
    public void sendAgain(){

        if (mToast != null)
            mToast.cancel();

        mViewModelLogin.confirmFor().observe(this, confirmCodeFor -> {
            if (confirmCodeFor.equals(FORGET_PASSWORD)){
                mViewModelForgetPassword.getResult().observe(CodeConfirmFragment.this, forgetPasswordModelResponse -> {
                    mViewModelForgetPassword.forgetPassword(forgetPasswordModelResponse.getEmail(), mLocale);
                    mViewModelForgetPassword.getResult().observe(CodeConfirmFragment.this, forgetPasswordModelResponse1 -> {
                        if (forgetPasswordModelResponse1.getKey().equals(SUCCESS)){
                            mToast = Toast.makeText(getContext(), forgetPasswordModelResponse1.getResult(), Toast.LENGTH_SHORT);
                            mToast.show();
                        }
                    });
                    mViewModelForgetPassword.isLoading().observe(CodeConfirmFragment.this, loading -> {
                        if (loading){
                            mProgress.setVisibility(View.VISIBLE);
                        } else {
                            mProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                });
            } else if (confirmCodeFor.equals(REGISTER)){
                mViewModelConfirmEmail.getEmail().observe(CodeConfirmFragment.this, new Observer<String>() {
                    @Override
                    public void onChanged(String email) {
                        mViewModelConfirmEmail.confirmEmail(email, mLocale);
                        mViewModelConfirmEmail.getResult().observe(CodeConfirmFragment.this, confirmEmailModelResponse -> {
                            if (confirmEmailModelResponse.getKey().equals(SUCCESS)) {
                                mToast = Toast.makeText(getContext(), confirmEmailModelResponse.getResult(), Toast.LENGTH_SHORT);
                                mToast.show();
                            }
                        });
                        mViewModelConfirmEmail.isLoading().observe(CodeConfirmFragment.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean loading) {
                                if (loading){
                                    mProgress.setVisibility(View.VISIBLE);
                                } else {
                                    mProgress.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void setEditTextsFocus() {
        mDigit1.requestFocus();
        mDigit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDigit2.requestFocus();
            }
        });

        mDigit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDigit3.requestFocus();
            }
        });

        mDigit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDigit4.requestFocus();
            }
        });
    }

    private void resetPassword(String enteredCode) {
        mViewModelForgetPassword.getResult().observe(this, forgetPasswordModelResponse -> {
            if (forgetPasswordModelResponse.getKey().equals(SUCCESS)) {
                String code = forgetPasswordModelResponse.getCode();
                if (code.equals(enteredCode)) {
                    new Handler().postDelayed(() -> {
                        mProgress.setVisibility(View.GONE);
                        loadFragment(CodeConfirmFragment.this.getActivity().getSupportFragmentManager(),
                                new PasswordResetFragment(), false);
                    }, 1296);
                } else {
                    new Handler().postDelayed(() -> {
                        mToast = Toast.makeText(getContext(), R.string.invalid_code, Toast.LENGTH_SHORT);
                        mToast.show();
                        mProgress.setVisibility(View.GONE);
                    }, 1296);
                }
            }
        });
    }

    private void register(String enteredCode) {
        mViewModelConfirmEmail.getResult().observe(this, confirmEmailModelResponse -> {
            if (confirmEmailModelResponse.getKey().equals(SUCCESS)) {
                String code = confirmEmailModelResponse.getCode();
                if (code.equals(enteredCode)) {
                    new Handler().postDelayed(() -> {
                        if (mToast != null)
                            mToast.cancel();
                        mProgress.setVisibility(View.GONE);
                        loadFragment(getActivity().getSupportFragmentManager(),
                                new RegisterFragment(), false);
                    }, 1296);
                } else {
                    new Handler().postDelayed(() -> {
                        if (mToast != null)
                            mToast.cancel();
                        mProgress.setVisibility(View.GONE);
                        mToast = Toast.makeText(getContext(), R.string.invalid_code, Toast.LENGTH_SHORT);
                        mToast.show();
                    }, 1296);
                }
            }
        });
    }
}
