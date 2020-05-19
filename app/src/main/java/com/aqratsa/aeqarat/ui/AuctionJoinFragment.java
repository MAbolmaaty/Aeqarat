package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.AccountBankViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionJoinFragment extends Fragment {

    @BindView(R.id.insuranceAmount)
    TextView mInsuranceAmount;
    @BindView(R.id.bankName)
    TextView mBankName;
    @BindView(R.id.accountNumber)
    TextView mAccountNumber;
    @BindView(R.id.iban)
    TextView mIBAN;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.toolbarAction1)
    ImageView mBack;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;

    private AccountBankViewModel mViewModelBankAccount;

    public AuctionJoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auction_join, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
        }
        mTitle.setText(R.string.auction_join);
        mViewModelBankAccount = ViewModelProviders.of(this).get(AccountBankViewModel.class);
        RealEstateViewModel viewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        viewModelRealEstate.getRealEstateId().observe(this, realEstateId -> {
            mViewModelBankAccount.bankAccount(realEstateId);
            mViewModelBankAccount.getBankAccount().observe(AuctionJoinFragment.this, bankAccountModelResponse -> {
                if (bankAccountModelResponse.getKey().equals(SUCCESS)) {
                    mConfirm.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.darkGrey));
                    mConfirm.setEnabled(true);
                    mInsuranceAmount.setText(bankAccountModelResponse.getBankAccount().getInsuranceAmount());
                    mAccountNumber.setText(bankAccountModelResponse.getBankAccount().getAccountNumber());
                    mBankName.setText(bankAccountModelResponse.getBankAccount().getBankName());
                    mIBAN.setText(bankAccountModelResponse.getBankAccount().getIBAN());
                }
            });
            mViewModelBankAccount.isLoading().observe(AuctionJoinFragment.this, loading -> {
                if (loading){
                    mProgress.setVisibility(View.VISIBLE);
                } else {
                    mProgress.setVisibility(View.INVISIBLE);
                }
            });
        });

        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.confirm)
    public void confirm() {
        loadFragment(AuctionJoinFragment.this.getActivity().getSupportFragmentManager(),
                new AuctionJoinConfirmFragment(),
                true);
    }

}
