package com.aqratsa.aeqarat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.models.request_ownership.response.RequestOwnershipModelResponse;
import com.aqratsa.aeqarat.models.request_maintenance.response.RequestMaintenanceModelResponse;
import com.aqratsa.aeqarat.models.request_rent.response.RequestRentModelResponse;
import com.aqratsa.aeqarat.models.request_termination.response.RequestTerminationModelResponse;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestMaintenanceViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestOwnershipViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestRentViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestTerminationViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestsUserViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_MAINTENANCE;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_OWNERSHIP;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_TERMINATION;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbarAction1)
    ImageView mBack;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;
    @BindView(R.id.title1)
    TextView mTitle1;
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.value1)
    TextView mValue1;
    @BindView(R.id.hint1)
    TextView mHint1;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.view2)
    View mView2;
    @BindView(R.id.value2)
    TextView mValue2;
    @BindView(R.id.hint2)
    TextView mHint2;
    @BindView(R.id.title3)
    TextView mTitle3;
    @BindView(R.id.value3)
    TextView mValue3;
    @BindView(R.id.icon3)
    ImageView mIcon3;
    @BindView(R.id.title4)
    TextView mTitle4;
    @BindView(R.id.spinner4)
    Spinner mSpinner4;
    @BindView(R.id.arrow4)
    ImageView mArrow4;
    @BindView(R.id.title5)
    TextView mTitle5;
    @BindView(R.id.spinner5)
    Spinner mSpinner5;
    @BindView(R.id.arrow5)
    ImageView mArrow5;
    @BindView(R.id.title6)
    TextView mTitle6;
    @BindView(R.id.value6)
    TextView mValue6;
    @BindView(R.id.send)
    Button mSend;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    private Toast mToast;
    private String mUserId;
    private String mRealEstateId;
    private String mDate;
    private String mMethodPayment;
    private String mMethodRefund;
    private String mTypeMaintenance;
    private String mDescriptionMaintenance;
    private String mLocale;
    private String mPriceForMonth;
    private String mPriceFor3Months;
    private String mPriceFor6Months;
    private String mPriceFor12Months;
    private String mDuration;

    private RealEstateViewModel mViewModelRealEstate;
    private RequestsUserViewModel mViewModelUserRequests;

    private static int REQUEST_TYPE;

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
        }

        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelUserRequests = ViewModelProviders.of(getActivity()).get(RequestsUserViewModel.class);
        mUserId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
        if (mUserId == null) {
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.getUser().observe(this, new Observer<LoginModelResponse>() {
                @Override
                public void onChanged(LoginModelResponse loginModelResponse) {
                    mUserId = loginModelResponse.getUser().getId();
                    mViewModelRealEstate.getRealEstate().observe(RequestFragment.this, realEstateModelResponse -> {
                        mRealEstateId = realEstateModelResponse.getRealEstate().getId();
                        mSend.setEnabled(true);
                    });
                }
            });
        } else {
            mViewModelRealEstate.getRealEstate().observe(RequestFragment.this, realEstateModelResponse -> {
                mRealEstateId = realEstateModelResponse.getRealEstate().getId();
                mSend.setEnabled(true);
            });
        }
        mViewModelRealEstate.getTypeRequest().observe(this, type -> {
            switch (type) {
                case REQUEST_OWNERSHIP:
                    viewRequestOwnership();
                    break;
                case REQUEST_RENT:
                    viewRequestRent();
                    break;
                case REQUEST_MAINTENANCE:
                    viewRequestMaintenance();
                    break;
                case REQUEST_TERMINATION:
                    viewRequestTermination();
                    break;

            }
        });
        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        mValue3.setText(date);
        mDate = date;
    }

    @OnClick(R.id.toolbarAction1)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.value3)
    public void pickDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.send)
    public void send() {
        if (mToast != null) {
            mToast.cancel();
        }

        switch (REQUEST_TYPE) {
            case REQUEST_OWNERSHIP:
                requestOwnership(mRealEstateId, mUserId, mDate, mMethodPayment, mLocale);
                break;
            case REQUEST_RENT:
                requestRent(mRealEstateId, mUserId, mDate, mDuration, mMethodPayment, mLocale);
                break;
            case REQUEST_MAINTENANCE:
                requestMaintenance(mRealEstateId, mUserId, mLocale);
                break;
            case REQUEST_TERMINATION:
                requestTermination(mRealEstateId, mUserId, mDate, mMethodRefund, mLocale);
                break;
        }

    }

    private void requestOwnership(String realEstateId, String userId, String startDate,
                                  String paymentMethod, String locale) {
        if (TextUtils.isEmpty(mDate)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_start_date), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        RequestOwnershipViewModel viewModelRequestOwnership =
                ViewModelProviders.of(this).get(RequestOwnershipViewModel.class);
        viewModelRequestOwnership.ownershipRequest(realEstateId, userId, startDate, paymentMethod, locale);
        viewModelRequestOwnership.getResult().observe(this, new Observer<RequestOwnershipModelResponse>() {
            @Override
            public void onChanged(RequestOwnershipModelResponse requestOwnershipModelResponse) {
                if (requestOwnershipModelResponse.getKey().equals(SUCCESS)) {
                    mViewModelUserRequests.userRequests(userId);
                    mViewModelUserRequests.getUserRequests().observe(RequestFragment.this, userRequestsModelResponse -> {
                        if (userRequestsModelResponse.getKey().equals(SUCCESS)) {
                            mToast = Toast.makeText(getContext(), requestOwnershipModelResponse.getResult(), Toast.LENGTH_SHORT);
                            mToast.show();
                            getActivity().onBackPressed();
                        }
                    });
                    mViewModelUserRequests.isLoading().observe(RequestFragment.this, loading -> {
                        if (loading) {
                            mProgress.setVisibility(View.VISIBLE);
                        } else {
                            mProgress.setVisibility(View.GONE);
                        }
                    });
                } else {
                    mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
        viewModelRequestOwnership.isLoading().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });
        viewModelRequestOwnership.failure().observe(this, failed -> {
            if (failed) {
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void viewRequestOwnership() {
        REQUEST_TYPE = REQUEST_OWNERSHIP;
        mTitle.setText(R.string.request_ownership);
        mTitle1.setVisibility(View.VISIBLE);
        mTitle1.setText(R.string.amount);
        mView1.setVisibility(View.VISIBLE);
        mValue1.setVisibility(View.VISIBLE);
        mHint1.setVisibility(View.VISIBLE);
        mHint1.setText(R.string.sar);
        mTitle3.setVisibility(View.VISIBLE);
        mTitle3.setText(R.string.date_start);
        mValue3.setVisibility(View.VISIBLE);
        mValue3.setText(R.string.date_start);
        mIcon3.setVisibility(View.VISIBLE);
        mTitle4.setVisibility(View.VISIBLE);
        mTitle4.setText(R.string.method_payment);
        mSpinner4.setVisibility(View.VISIBLE);
        addMethodsPayment(mSpinner4);
        mArrow4.setVisibility(View.VISIBLE);
        mSend.setVisibility(View.VISIBLE);
        mViewModelRealEstate.getRealEstate().observe(RequestFragment.this, realEstateModelResponse -> {
            mValue1.setText(realEstateModelResponse.getRealEstate().getTotal_amount());
        });
    }

    private void requestRent(String realEstateId, String userId, String startDate,
                             String duration, String paymentMethod, String locale) {
        if (TextUtils.isEmpty(mDate)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_start_date), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        RequestRentViewModel viewModelRequestRent =
                ViewModelProviders.of(this).get(RequestRentViewModel.class);
        viewModelRequestRent.rentRequest(realEstateId, userId, startDate, duration, paymentMethod, locale);
        viewModelRequestRent.getResult().observe(this, new Observer<RequestRentModelResponse>() {
            @Override
            public void onChanged(RequestRentModelResponse requestRentModelResponse) {
                if (requestRentModelResponse.getKey().equals(SUCCESS)) {
                    mViewModelUserRequests.userRequests(userId);
                    mViewModelUserRequests.getUserRequests().observe(RequestFragment.this, userRequestsModelResponse -> {
                        if (userRequestsModelResponse.getKey().equals(SUCCESS)) {
                            mToast = Toast.makeText(getContext(), requestRentModelResponse.getResult(), Toast.LENGTH_SHORT);
                            mToast.show();
                            getActivity().onBackPressed();
                        }
                    });
                    mViewModelUserRequests.isLoading().observe(RequestFragment.this, loading -> {
                        if (loading) {
                            mProgress.setVisibility(View.VISIBLE);
                        } else {
                            mProgress.setVisibility(View.GONE);
                        }
                    });
                } else {
                    mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
        viewModelRequestRent.isLoading().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });
        viewModelRequestRent.failure().observe(this, failed -> {
            if (failed) {
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void viewRequestRent() {
        REQUEST_TYPE = REQUEST_RENT;
        mTitle.setText(R.string.request_rent);
        mTitle1.setVisibility(View.VISIBLE);
        mTitle1.setText(R.string.amount);
        mView1.setVisibility(View.VISIBLE);
        mValue1.setVisibility(View.VISIBLE);
        mHint1.setVisibility(View.VISIBLE);
        mHint1.setText(R.string.sar);

        mTitle2.setVisibility(View.VISIBLE);
        mTitle2.setText(R.string.amount_insurance);
        mView2.setVisibility(View.VISIBLE);
        mValue2.setVisibility(View.VISIBLE);

        mHint2.setVisibility(View.VISIBLE);
        mHint2.setText(R.string.sar);

        mTitle3.setVisibility(View.VISIBLE);
        mTitle3.setText(R.string.date_start);
        mValue3.setVisibility(View.VISIBLE);
        mValue3.setText(R.string.date_start);
        mIcon3.setVisibility(View.VISIBLE);

        mTitle4.setVisibility(View.VISIBLE);
        mTitle4.setText(R.string.duration);
        mSpinner4.setVisibility(View.VISIBLE);
        addDurations(mSpinner4);
        mArrow4.setVisibility(View.VISIBLE);

        mTitle5.setVisibility(View.VISIBLE);
        mTitle5.setText(R.string.method_payment);
        mSpinner5.setVisibility(View.VISIBLE);
        addMethodsPayment(mSpinner5);
        mArrow5.setVisibility(View.VISIBLE);

        mSend.setVisibility(View.VISIBLE);

        mViewModelRealEstate.getRealEstate().observe(RequestFragment.this, realEstateModelResponse -> {
            if (realEstateModelResponse.getRealEstate().getPrice_for_month() != null) {
                mValue1.setText(mPriceForMonth);
            } else if (realEstateModelResponse.getRealEstate().getPrice_for_3month() != null) {
                mValue1.setText(mPriceFor3Months);
            } else if (realEstateModelResponse.getRealEstate().getPrice_for_6month() != null) {
                mValue1.setText(mPriceFor6Months);
            } else if (realEstateModelResponse.getRealEstate().getPrice_for_12month() != null) {
                mValue1.setText(mPriceFor12Months);
            }

            mPriceForMonth = realEstateModelResponse.getRealEstate().getPrice_for_month();
            mPriceFor3Months = realEstateModelResponse.getRealEstate().getPrice_for_3month();
            mPriceFor6Months = realEstateModelResponse.getRealEstate().getPrice_for_6month();
            mPriceFor12Months = realEstateModelResponse.getRealEstate().getPrice_for_12month();

            mValue2.setText(realEstateModelResponse.getRealEstate().getAmount_insurance());

        });
    }

    private void requestMaintenance(String realEstateId, String userId, String locale) {
        if (TextUtils.isEmpty(mValue6.getText().toString())){
            mToast = Toast.makeText(getActivity(),
                    R.string.enter_description, Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
        mTypeMaintenance = "1";
        mDescriptionMaintenance = mValue6.getText().toString();
        RequestMaintenanceViewModel viewModelRequestMaintenance =
                ViewModelProviders.of(this).get(RequestMaintenanceViewModel.class);
        viewModelRequestMaintenance.maintenance(realEstateId, userId, mTypeMaintenance, mDescriptionMaintenance, locale);
        viewModelRequestMaintenance.getResult().observe(this, new Observer<RequestMaintenanceModelResponse>() {
            @Override
            public void onChanged(RequestMaintenanceModelResponse requestMaintenanceModelResponse) {
                if (requestMaintenanceModelResponse.getKey().equals(SUCCESS)){
                    mToast = Toast.makeText(getActivity(),
                            requestMaintenanceModelResponse.getResult(), Toast.LENGTH_SHORT);
                    mToast.show();
                    getActivity().onBackPressed();
                } else {
                    mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
        viewModelRequestMaintenance.isLoading().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });
        viewModelRequestMaintenance.failure().observe(this, failed -> {
            if (failed) {
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void viewRequestMaintenance() {
        REQUEST_TYPE = REQUEST_MAINTENANCE;
        mTitle.setText(R.string.request_maintenance);
        mTitle4.setVisibility(View.VISIBLE);
        mTitle4.setText(R.string.type_maintenance);
        mSpinner4.setVisibility(View.VISIBLE);
        mArrow4.setVisibility(View.VISIBLE);

        mTitle6.setVisibility(View.VISIBLE);
        mTitle6.setText(R.string.other_info);
        mValue6.setVisibility(View.VISIBLE);

        mSend.setVisibility(View.VISIBLE);
    }

    private void requestTermination(String realEstateId, String userId, String dateExit,
                                    String methodRefund, String locale) {
        if (TextUtils.isEmpty(mDate)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_exit_date), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        SoftKeyboard.dismissKeyboardInActivity(getActivity());

        RequestTerminationViewModel viewModelRequestTermination =
                ViewModelProviders.of(this).get(RequestTerminationViewModel.class);
        viewModelRequestTermination.terminateContract(realEstateId, userId, dateExit, methodRefund, locale);
        viewModelRequestTermination.getResult().observe(this, new Observer<RequestTerminationModelResponse>() {
            @Override
            public void onChanged(RequestTerminationModelResponse requestTerminationModelResponse) {
                if (requestTerminationModelResponse.getKey().equals(SUCCESS)){
                    mToast = Toast.makeText(getActivity(),
                            requestTerminationModelResponse.getResult(), Toast.LENGTH_SHORT);
                    mToast.show();
                    getActivity().onBackPressed();
                } else {
                    mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
        viewModelRequestTermination.isLoading().observe(this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });
        viewModelRequestTermination.failure().observe(this, failed -> {
            if (failed) {
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void viewRequestTermination() {
        REQUEST_TYPE = REQUEST_TERMINATION;
        mTitle.setText(R.string.request_termination);
        mTitle1.setVisibility(View.VISIBLE);
        mTitle1.setText(R.string.amount_insurance);
        mView1.setVisibility(View.VISIBLE);
        mValue1.setVisibility(View.VISIBLE);
        mHint1.setVisibility(View.VISIBLE);
        mHint1.setText(R.string.sar);

        mTitle3.setVisibility(View.VISIBLE);
        mTitle3.setText(R.string.date_exit);
        mValue3.setVisibility(View.VISIBLE);
        mValue3.setText(R.string.date_exit);
        mIcon3.setVisibility(View.VISIBLE);

        mTitle4.setVisibility(View.VISIBLE);
        mTitle4.setText(R.string.method_refund);
        mSpinner4.setVisibility(View.VISIBLE);
        addMethodsRefund(mSpinner4);
        mArrow4.setVisibility(View.VISIBLE);

        mTitle6.setVisibility(View.VISIBLE);
        mTitle6.setText(R.string.other_info);
        mValue6.setVisibility(View.VISIBLE);

        mSend.setVisibility(View.VISIBLE);
        mViewModelRealEstate.getRealEstate().observe(RequestFragment.this, realEstateModelResponse -> {
            mValue1.setText(realEstateModelResponse.getRealEstate().getAmount_insurance());
        });
    }
    private void addMethodsPayment(Spinner spinner) {
        ArrayList<String> listMethodsPayment = new ArrayList<>();
        listMethodsPayment.add(getString(R.string.cash));
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listMethodsPayment);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mMethodPayment = "1";
                        break;
                    case 1:
                        mMethodPayment = "2";
                        break;
                    case 2:
                        mMethodPayment = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addMethodsRefund(Spinner spinner) {
        ArrayList<String> listMethodsRefund = new ArrayList<>();
        listMethodsRefund.add(getString(R.string.cash));
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listMethodsRefund);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mMethodRefund = "1";
                        break;
                    case 1:
                        mMethodRefund = "2";
                        break;
                    case 2:
                        mMethodRefund = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addDurations(Spinner spinner) {
        ArrayList<String> listDurations = new ArrayList<>();
        listDurations.add(getString(R.string.month));
        listDurations.add(getString(R.string.three_months));
        listDurations.add(getString(R.string.six_months));
        listDurations.add(getString(R.string.twelve_months));
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listDurations);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mDuration = "1";
                        animateAmountValue2(mDuration);
                        break;
                    case 1:
                        mDuration = "3";
                        animateAmountValue2(mDuration);
                        break;
                    case 2:
                        mDuration = "6";
                        animateAmountValue2(mDuration);
                        break;
                    case 3:
                        mDuration = "12";
                        animateAmountValue2(mDuration);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void animateAmountValue(String duration) {
        if (duration.equals("1")) {
            mValue1.setText(mPriceForMonth);
        } else if (duration.equals("3")) {
            mValue1.animate().scaleY(1.2f);
            mValue1.animate().scaleX(1.2f);
            new Handler().postDelayed(() -> {
                mValue1.animate().scaleY(1);
                mValue1.animate().scaleX(1);
            }, 496);
            new Handler().postDelayed(() -> mValue1.setText(mPriceFor3Months), 296);
        } else if (duration.equals("6")) {
            mValue1.animate().scaleY(2);
            mValue1.animate().scaleX(1.2f);
            new Handler().postDelayed(() -> {
                mValue1.animate().scaleY(1);
                mValue1.animate().scaleX(1);
            }, 496);
            new Handler().postDelayed(() -> mValue1.setText(mPriceFor6Months), 296);
        } else if (duration.equals("12")) {
            mValue1.animate().scaleY(2);
            mValue1.animate().scaleX(1.2f);
            new Handler().postDelayed(() -> {
                mValue1.animate().scaleY(1);
                mValue1.animate().scaleX(1);
            }, 496);
            new Handler().postDelayed(() -> mValue1.setText(mPriceFor12Months), 296);
        }
    }

    private void animateAmountValue2(String duration){
        if (duration.equals("1")) {
            mValue1.setText(mPriceForMonth);
        } else if (duration.equals("3")) {
            mValue1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake100));
            new Handler().postDelayed(() -> mValue1.setText(mPriceFor3Months), 296);
        } else if (duration.equals("6")) {
            mValue1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake100));
            new Handler().postDelayed(() -> mValue1.setText(mPriceFor6Months), 296);
        } else if (duration.equals("12")) {
            mValue1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake100));
            new Handler().postDelayed(() -> mValue1.setText(mPriceFor12Months), 296);
        }
    }
}
