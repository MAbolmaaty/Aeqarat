package com.aqratsa.aeqarat.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.RealEstateRequestsAdapter;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateRequestsTerminationFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.noRequests)
    TextView mNoRequests;

    private RealEstateRequestsAdapter mAdapterRealEstateRequests;

    public RealEstateRequestsTerminationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_estate_requests_termination, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar"))
            view.setRotation(-180);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        loadRequests();
        return view;
    }

    public static RealEstateRequestsTerminationFragment newInstance(){return new RealEstateRequestsTerminationFragment();}

    private void loadRequests(){
        mNoRequests.setVisibility(View.VISIBLE);
    }
}
