package com.aqratsa.aeqarat.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateRequestModifyFragment extends Fragment {

    @BindView(R.id.toolbarAction1)
    ImageView mBack;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.pending)
    Button mPending;
    @BindView(R.id.negotiated)
    Button mNegotiated;
    @BindView(R.id.negotiate)
    Button mNegotiate;

    public RealEstateRequestModifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_estate_request_modify, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
        }
        mTitle.setText(R.string.request_modify);
        mDelete.setVisibility(View.VISIBLE);

        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.pending)
    public void pending(){
        mPending.setEnabled(false);
        mPending.setTextColor(Color.WHITE);
        mNegotiate.setEnabled(true);
        mNegotiate.setTextColor(getResources().getColor(R.color.darkGrey));
        mNegotiated.setEnabled(true);
        mNegotiated.setTextColor(getResources().getColor(R.color.darkGrey));
    }

    @OnClick(R.id.negotiated)
    public void negotiated(){
        mPending.setEnabled(true);
        mPending.setTextColor(getResources().getColor(R.color.darkGrey));
        mNegotiate.setEnabled(true);
        mNegotiate.setTextColor(getResources().getColor(R.color.darkGrey));
        mNegotiated.setEnabled(false);
        mNegotiated.setTextColor(Color.WHITE);
    }

    @OnClick(R.id.negotiate)
    public void negotiate(){
        mPending.setEnabled(true);
        mPending.setTextColor(getResources().getColor(R.color.darkGrey));
        mNegotiate.setEnabled(false);
        mNegotiate.setTextColor(Color.WHITE);
        mNegotiated.setEnabled(true);
        mNegotiated.setTextColor(getResources().getColor(R.color.darkGrey));
    }
}
