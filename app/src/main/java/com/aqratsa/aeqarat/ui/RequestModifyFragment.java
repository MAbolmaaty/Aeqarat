package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestModifyViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestSubmittedViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_OWNERSHIP;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_RENT;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestModifyFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = RequestModifyFragment.class.getSimpleName();

    @BindView(R.id.toolbarAction1)
    ImageView mBack;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;
    @BindView(R.id.title1)
    TextView mTitle1;
    @BindView(R.id.value1)
    TextView mValue1;
    @BindView(R.id.icon1)
    ImageView mIcon1;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.spinner2)
    Spinner mSpinner2;
    @BindView(R.id.icon2)
    ImageView mIcon2;
    @BindView(R.id.title3)
    TextView mTitle3;
    @BindView(R.id.spinner3)
    Spinner mSpinner3;
    @BindView(R.id.icon3)
    ImageView mIcon3;
    @BindView(R.id.title4)
    TextView mTitle4;
    @BindView(R.id.value4)
    EditText mValue4;
    @BindView(R.id.save)
    Button mSave;
    @BindView(R.id.cancel)
    Button mCancel;

    private List<String> mListDurations = new ArrayList<>();
    private List<String> mListPaymentMethods = new ArrayList<>();

    private RealEstateViewModel mViewModelRealEstate;
    private RequestModifyViewModel mViewModelModifyRequest;
    private RequestSubmittedViewModel mViewModelSubmittedRequest;

    private String mRequestId;
    private int mRequestDuration;
    private String mRequestPayment;
    private String mDate;
    private Toast mToast;
    private String mLocale;

    private static int REQUEST_TYPE;
    private String mMethodPayment;

    public RequestModifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_request, container, false);
        ButterKnife.bind(this, view);
        mTitle.setText(R.string.request_modify);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
        }

        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelModifyRequest = ViewModelProviders.of(this).get(RequestModifyViewModel.class);
        mViewModelSubmittedRequest = ViewModelProviders.of(getActivity()).get(RequestSubmittedViewModel.class);

        mViewModelRealEstate.getTypeRequest().observe(this, type -> {
            switch (type) {
                case REQUEST_OWNERSHIP:
                    viewRequestOwnership();
                    break;
                case REQUEST_RENT:
                    viewRequestRent();
                    break;
                /*case REQUEST_MAINTENANCE:
                    viewRequestMaintenance();
                    break;
                case REQUEST_TERMINATION:
                    viewRequestTermination();
                    break;*/

            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        mValue1.setText(date);
        mDate = date;
    }

    @OnClick(R.id.value1)
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

    @OnClick({R.id.toolbarAction1, R.id.cancel})
    public void back() {
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
        getActivity().onBackPressed();
    }

    @OnClick(R.id.save)
    public void modifyRequest() {
        if (mToast != null)
            mToast.cancel();
        SoftKeyboard.dismissKeyboardInActivity(getActivity());

        switch (REQUEST_TYPE) {
            case REQUEST_OWNERSHIP:
                modifyRequestOwnership(mDate, mMethodPayment, mRequestId, mLocale);
                break;
            case REQUEST_RENT:
                modifyRequestRent(mDate, mRequestDuration, mMethodPayment, mRequestId, mLocale);
                break;
            /*case REQUEST_MAINTENANCE:
                requestMaintenance(mRealEstateId, mUserId, mLocale);
                break;
            case REQUEST_TERMINATION:
                requestTermination(mRealEstateId, mUserId, mDate, mMethodRefund, mLocale);
                break;*/
        }
    }

    private void modifyRequestOwnership(String dateStart, String methodPayment, String requestId,
                                        String locale) {
        if (TextUtils.isEmpty(mDate)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_start_date), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        mViewModelModifyRequest.modifyRequest(dateStart, null, methodPayment, requestId, locale);
        mViewModelModifyRequest.getResult().observe(this, modifyRequestModelResponse -> {
            if (modifyRequestModelResponse.getKey().equals(SUCCESS)) {
                mToast = Toast.makeText(getContext(), modifyRequestModelResponse.getResult(), Toast.LENGTH_SHORT);
                mToast.show();
                getActivity().onBackPressed();
            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });

        mViewModelModifyRequest.isLoading().observe(this, loading ->
                mProgress.setVisibility(loading ? View.VISIBLE : View.GONE));

        mViewModelModifyRequest.failure().observe(this, failed -> {
            if (failed) {
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });

    }

    private void viewRequestOwnership() {
        REQUEST_TYPE = REQUEST_OWNERSHIP;
        mTitle1.setVisibility(View.VISIBLE);
        mTitle1.setText(R.string.date_start);
        mValue1.setVisibility(View.VISIBLE);
        mIcon1.setVisibility(View.VISIBLE);

        mTitle2.setVisibility(View.VISIBLE);
        mTitle2.setText(R.string.method_payment);
        mSpinner2.setVisibility(View.VISIBLE);
        addMethodsPayment(mSpinner2);
        mIcon2.setVisibility(View.VISIBLE);

        mTitle4.setVisibility(View.VISIBLE);
        mValue4.setVisibility(View.VISIBLE);

        mViewModelSubmittedRequest.getResult().observe(this, submittedRequestModelResponse -> {
            mRequestId = submittedRequestModelResponse.getResult().getId();
            mRequestPayment = submittedRequestModelResponse.getResult().getPay_way();
            String startDate = submittedRequestModelResponse.getResult().getAccess_date();
            if (startDate == null) {
                startDate = submittedRequestModelResponse.getResult().getAttendees_date();
            }
            mDate = startDate;
            mValue1.setText(startDate);
        });
    }

    private void modifyRequestRent(String dateStart, int duration, String methodPayment, String requestId,
                                        String locale) {
        if (TextUtils.isEmpty(mDate)) {
            mToast = Toast.makeText(getContext(), getString(R.string.enter_start_date), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        mViewModelModifyRequest.modifyRequest(dateStart, String.valueOf(duration), methodPayment, requestId, locale);
        mViewModelModifyRequest.getResult().observe(this, modifyRequestModelResponse -> {
            if (modifyRequestModelResponse.getKey().equals(SUCCESS)) {
                mToast = Toast.makeText(getContext(), modifyRequestModelResponse.getResult(), Toast.LENGTH_SHORT);
                mToast.show();
                getActivity().onBackPressed();
            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });

        mViewModelModifyRequest.isLoading().observe(this, loading ->
                mProgress.setVisibility(loading ? View.VISIBLE : View.GONE));

        mViewModelModifyRequest.failure().observe(this, failed -> {
            if (failed) {
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });

    }

    private void viewRequestRent() {
        REQUEST_TYPE = REQUEST_RENT;
        mTitle1.setVisibility(View.VISIBLE);
        mTitle1.setText(R.string.date_start);
        mValue1.setVisibility(View.VISIBLE);
        mIcon1.setVisibility(View.VISIBLE);

        mTitle2.setVisibility(View.VISIBLE);
        mTitle2.setText(R.string.duration);
        mSpinner2.setVisibility(View.VISIBLE);
        addDurations(mSpinner2);
        mIcon2.setVisibility(View.VISIBLE);

        mTitle3.setVisibility(View.VISIBLE);
        mTitle3.setText(R.string.method_payment);
        mSpinner3.setVisibility(View.VISIBLE);
        addMethodsPayment(mSpinner3);
        mIcon3.setVisibility(View.VISIBLE);

        mTitle4.setVisibility(View.VISIBLE);
        mValue4.setVisibility(View.VISIBLE);

        mViewModelSubmittedRequest.getResult().observe(this, submittedRequestModelResponse -> {
            mRequestId = submittedRequestModelResponse.getResult().getId();
            mRequestPayment = submittedRequestModelResponse.getResult().getPay_way();
            mRequestDuration = Integer.parseInt(submittedRequestModelResponse.getResult().getDuration());
            String startDate = submittedRequestModelResponse.getResult().getAccess_date();
            if (startDate == null) {
                startDate = submittedRequestModelResponse.getResult().getAttendees_date();
            }
            mDate = startDate;
            mValue1.setText(startDate);
            setDuration(mSpinner2, mRequestDuration);
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
                        mRequestDuration = 1;
                        break;
                    case 1:
                        mRequestDuration = 3;
                        break;
                    case 2:
                        mRequestDuration = 6;
                        break;
                    case 3:
                        mRequestDuration = 12;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDuration(Spinner spinner, int duration){
        switch (duration){
            case 1:
                spinner.setSelection(0);
                break;
            case 3:
                spinner.setSelection(1);
                break;
            case 6:
                spinner.setSelection(2);
                break;
            case 12:
                spinner.setSelection(3);
                break;
        }

    }
}
