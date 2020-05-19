package com.aqratsa.aeqarat.ui;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.RealEstatesAgentAdapter;
import com.aqratsa.aeqarat.models.search.response.RealEstate;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstatesAgentFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.noRealEstates)
    TextView mNoRealEstates;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;

    private Toast mToast;

    public RealEstatesAgentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED, GravityCompat.START);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_estates_agent, container, false);
        ButterKnife.bind(this, view);
        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.real_estates_responsible_for);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        loadRealEstates();
        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void menu(){
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    private void loadRealEstates(){
        if (mToast != null)
            mToast.cancel();
        ArrayList<RealEstate> listRealEstates = new ArrayList<>();
        RealEstateViewModel viewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        SearchViewModel viewModelSearch = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        viewModelSearch.search(null, null, null, null, "",
                null, null, null, null, null, 0,
                0);
        viewModelSearch.getResults().observe(RealEstatesAgentFragment.this, searchModelResponse -> {
            if (searchModelResponse.getKey().equals(SUCCESS)) {
                listRealEstates.clear();
                listRealEstates.addAll(Arrays.asList(searchModelResponse.getRealEstates()));
                if (listRealEstates.size() < 1) {
                    mNoRealEstates.setVisibility(View.VISIBLE);
                } else {
                    mNoRealEstates.setVisibility(View.GONE);
                }
                RealEstatesAgentAdapter adapter =
                        new RealEstatesAgentAdapter(RealEstatesAgentFragment.this.getContext(),
                                listRealEstates, position -> {
                    viewModelRealEstate.setRealEstateId(listRealEstates.get(position).getId());
                    loadFragment(RealEstatesAgentFragment.this.getActivity().getSupportFragmentManager(),
                            new RealEstateFragment(), true);

                });
                mRecyclerView.setAdapter(adapter);
            } else {
                mToast = Toast.makeText(RealEstatesAgentFragment.this.getActivity(),
                        R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
                mNoRealEstates.setVisibility(View.VISIBLE);
            }
        });

        viewModelSearch.isLoading().observe(RealEstatesAgentFragment.this, loading -> {
            if (loading) {
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });

        viewModelSearch.failure().observe(RealEstatesAgentFragment.this, failure -> {
            if (failure){
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
                mNoRealEstates.setVisibility(View.VISIBLE);
            }
        });
    }
}
