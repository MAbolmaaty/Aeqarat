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
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.ContactUsViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.hbb20.CountryCodePicker;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    private static final String TAG = ContactUsFragment.class.getSimpleName();

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.messageTitle)
    EditText mMessageTitle;
    @BindView(R.id.message)
    EditText mMessage;
    @BindView(R.id.send)
    Button mSend;
    @BindView(R.id.countryCodePicker)
    CountryCodePicker mCountryCodePicker;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;

    private ContactUsViewModel mViewModelContactUs;
    private Toast mToast;
    private String mLocale;
    private InfoUserViewModel mViewModelInfoUser;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);
        mViewModelContactUs = ViewModelProviders.of(this).get(ContactUsViewModel.class);
        mViewModelInfoUser = ViewModelProviders.of(getActivity()).get(InfoUserViewModel.class);
        String userId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
        if (userId == null) {
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.isLoggedIn().observe(this, loggedIn -> {
                if (loggedIn) {
                    viewModelLogin.getUser().observe(ContactUsFragment.this, loginModelResponse ->
                            loadUserInfo(loginModelResponse.getUser().getId()));
                }
            });

        } else {
            loadUserInfo(userId);
        }

        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());

        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.contact_us);
        mCountryCodePicker.setCountryForPhoneCode(+966);

        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void menu() {
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    @OnClick(R.id.send)
    public void contact() {
        String username = mUsername.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String email = mEmail.getText().toString();
        String messageTitle = mMessageTitle.getText().toString();
        String messageText = mMessage.getText().toString();

        if (mToast != null)
            mToast.cancel();

        if (TextUtils.isEmpty(username)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_username), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_phone_number), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_email), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (TextUtils.isEmpty(messageTitle)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_title), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (TextUtils.isEmpty(messageText)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_message), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        String countryCode = "+" + mCountryCodePicker.getSelectedCountryCode();

        SoftKeyboard.dismissKeyboardInActivity(getContext());

        mViewModelContactUs.contactUs(username, email, countryCode, phoneNumber, messageText, messageTitle, mLocale);
        mViewModelContactUs.getResult().observe(this, contactUsModelResponse -> {
            if (contactUsModelResponse.getKey().equals(SUCCESS)) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getContext(), contactUsModelResponse.getResult(), Toast.LENGTH_SHORT);
                mToast.show();
                mUsername.setText("");
                mPhoneNumber.setText("");
                mEmail.setText("");
                mMessageTitle.setText("");
                mMessage.setText("");
            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();

            }
        });
        mViewModelContactUs.isLoading().observe(this, loading -> progress(loading));
        mViewModelContactUs.failure().observe(this, failure -> {
            if (failure) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void progress(boolean loading) {
        MainActivity.progress(loading, mProgressBar, mSend);
    }

    private void loadUserInfo(String userId) {
        mViewModelInfoUser.userInfo(userId);
        mViewModelInfoUser.getUserInfo().observe(ContactUsFragment.this, userInfoModelResponse -> {
            if (userInfoModelResponse.getKey().equals(SUCCESS)) {
                mUsername.setText(userInfoModelResponse.getUser().getName());
                if (userInfoModelResponse.getUser().getCode() != null)
                    mCountryCodePicker.setCountryForPhoneCode(Integer.parseInt(userInfoModelResponse.getUser().getCode()));
                mPhoneNumber.setText(userInfoModelResponse.getUser().getPhone());
                mEmail.setText(userInfoModelResponse.getUser().getEmail());
            }
        });
        mViewModelInfoUser.isLoading().observe(ContactUsFragment.this, loading -> {
            if (loading) {
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
