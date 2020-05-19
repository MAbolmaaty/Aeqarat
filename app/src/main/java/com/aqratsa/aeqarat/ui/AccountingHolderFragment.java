package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.real_estate.RealEstateModelResponse;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountingHolderFragment extends Fragment {

    @BindView(R.id.toolbarAction1)
    ImageView mBack;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;
    @BindView(R.id.income)
    TextView mIncome;
    @BindView(R.id.expenses)
    TextView mExpenses;
    @BindView(R.id.commission)
    TextView mCommission;
    @BindView(R.id.balance)
    TextView mBalance;

    public AccountingHolderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounting_holder, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
        }
        mTitle.setText(R.string.accounting);
        RealEstateViewModel viewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        viewModelRealEstate.getRealEstate().observe(this, new Observer<RealEstateModelResponse>() {
            @Override
            public void onChanged(RealEstateModelResponse realEstateModelResponse) {
                mCommission.setText(realEstateModelResponse.getRealEstate().getCommission());
                mIncome.setText(realEstateModelResponse.getRealEstate().getEarnings());
                mExpenses.setText(realEstateModelResponse.getRealEstate().getExpenses());
                mBalance.setText(realEstateModelResponse.getRealEstate().getRevenues());
            }
        });
        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void back(){
        getActivity().onBackPressed();
    }
}
