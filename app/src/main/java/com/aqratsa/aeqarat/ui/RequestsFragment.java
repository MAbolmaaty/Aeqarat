package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.RequestsUserAdapter;
import com.aqratsa.aeqarat.models.requests_user.Request;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestsUserViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private static final String TAG = RequestsFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.noRequests)
    TextView mNoRequests;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;

    private String mUserId;
    private RequestsUserAdapter mAdapterUserRequests;
    private RealEstateViewModel mViewModelRealEstate;
    private List<Request> mListRequests = new ArrayList<>();
    private Toast mToast;

    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, view);
        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.requests);

        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mUserId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
        if (mUserId == null) {
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.getUser().observe(this, loginModelResponse -> {
                mUserId = loginModelResponse.getUser().getId();
                loadRealEstates(mUserId);
            });
        } else {
            loadRealEstates(mUserId);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED, GravityCompat.START);
    }

    @OnClick(R.id.toolbarAction1)
    public void menu(){
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    private void loadRealEstates(String userId){
        if (mToast != null)
            mToast.cancel();
        RequestsUserViewModel viewModelUserRequests = ViewModelProviders.of(getActivity()).get(RequestsUserViewModel.class);
        viewModelUserRequests.userRequests(userId);
        viewModelUserRequests.getUserRequests().observe(this, userRequestsModelResponse -> {
            if (userRequestsModelResponse.getKey().equals(SUCCESS)) {
                mListRequests.clear();
                mListRequests.addAll(Arrays.asList(userRequestsModelResponse.getRequests()));
                if (mListRequests.size() < 1){
                    mNoRequests.setVisibility(View.VISIBLE);
                } else {
                    mNoRequests.setVisibility(View.GONE);
                }
                mAdapterUserRequests = new RequestsUserAdapter(getContext(), mListRequests, position -> {
                    mViewModelRealEstate.setRealEstateId(mListRequests.get(position).getAkar_id());
                    loadFragment( RequestsFragment.this.getActivity().getSupportFragmentManager(),
                            new RealEstateFragment(), true);
                });
                mRecyclerView.setAdapter(mAdapterUserRequests);
            }else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
        viewModelUserRequests.isLoading().observe(this, loading -> {
            if (loading){
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
        });
        viewModelUserRequests.failure().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean failed) {
                if (failed){
                    mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
    }
}
