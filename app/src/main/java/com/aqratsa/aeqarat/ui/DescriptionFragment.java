package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {

    @BindView(R.id.description)
    TextView mTextViewDescription;
    @BindView(R.id.shimmer1)
    View mShimmer1;
    @BindView(R.id.shimmer2)
    View mShimmer2;
    @BindView(R.id.shimmer3)
    View mShimmer3;

    private RealEstateViewModel mViewModelRealEstate;
    private String mLocale;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar"))
            view.setRotation(-180);
        ButterKnife.bind(this, view);
        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelRealEstate.getRealEstate().observe(this, realEstateModelResponse -> {
            if (realEstateModelResponse.getRealEstate() != null){
                mShimmer1.setVisibility(View.GONE);
                mShimmer2.setVisibility(View.GONE);
                mShimmer3.setVisibility(View.GONE);
                mTextViewDescription.setText(realEstateModelResponse.getRealEstate().getDescribtion());
            }
        });

        return view;
    }

    public static DescriptionFragment newInstance(){
        return new DescriptionFragment();
    }
}
