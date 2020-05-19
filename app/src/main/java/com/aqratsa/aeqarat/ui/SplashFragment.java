package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.DistrictsViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateCategoriesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateStatusesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstatesViewModel;
import com.aqratsa.aeqarat.viewmodels.RegionsViewModel;
import com.aqratsa.aeqarat.viewmodels.SlidesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.FIRST_INSTALL;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    @BindView(R.id.progressBar1)
    ProgressBar mProgressBar1;
    @BindView(R.id.progressBar2)
    ProgressBar mProgressBar2;
    @BindView(R.id.progressBar3)
    ProgressBar mProgressBar3;
    @BindView(R.id.background)
    ImageView mBackground;
    @BindView(R.id.logo)
    ImageView mLogo;

    private RealEstatesViewModel mViewModelRealEstates;
    private SlidesViewModel mViewModelSlides;
    private RealEstateStatusesViewModel mViewModelStatuses;
    private RealEstateCategoriesViewModel mViewModelCategories;
    private RegionsViewModel mViewModelRegions;
    private DistrictsViewModel mViewModelDistricts;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);

        mViewModelRealEstates = ViewModelProviders.of(getActivity()).get(RealEstatesViewModel.class);
        mViewModelSlides = ViewModelProviders.of(getActivity()).get(SlidesViewModel.class);
        mViewModelStatuses = ViewModelProviders.of(getActivity()).get(RealEstateStatusesViewModel.class);
        mViewModelCategories = ViewModelProviders.of(getActivity()).get(RealEstateCategoriesViewModel.class);
        mViewModelRegions = ViewModelProviders.of(getActivity()).get(RegionsViewModel.class);
        mViewModelDistricts = ViewModelProviders.of(getActivity()).get(DistrictsViewModel.class);

        //Waiting in splash for loading critical data
        new Handler().postDelayed(() -> loadCriticalData(), 1296);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
    }

    private void loadCriticalData() {
        mProgressBar1.setProgress(100);
        mProgressBar2.setProgress(8);
        mViewModelRealEstates.isLoading().observe(this, realEstatesLoading -> {
            if (!realEstatesLoading) {
                mProgressBar2.setProgress(30);
                mViewModelSlides.isLoading().observe(SplashFragment.this, slidesLoading -> {
                    if (!slidesLoading) {
                        mViewModelStatuses.isLoading().observe(SplashFragment.this, statusesLoading -> {
                            if (!statusesLoading) {
                                mProgressBar2.setProgress(60);
                                mViewModelCategories.isLoading().observe(SplashFragment.this, categoriesLoading -> {
                                    if (!categoriesLoading) {
                                        mViewModelRegions.isLoading().observe(SplashFragment.this, regionsLoading -> {
                                            if (!regionsLoading) {
                                                mViewModelDistricts.isLoading().observe(SplashFragment.this, districtsLoading -> {
                                                    if (!districtsLoading) {
                                                        mProgressBar2.setProgress(100);
                                                        mProgressBar3.setProgress(8);
                                                        boolean firstInstall = SharedPrefUtil.getInstance(getContext()).read(FIRST_INSTALL, true);
                                                        if (firstInstall) {
                                                            viewSlides();
                                                        } else {
                                                            home();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void viewSlides() {
        mProgressBar3.setProgress(100);
        loadFragment(SplashFragment.this.getActivity().getSupportFragmentManager(),
                new SlidesShowFragment(), false);
    }

    private void login() {
        mProgressBar3.setProgress(100);
        loadFragment(SplashFragment.this.getActivity().getSupportFragmentManager(),
                new LoginFragment(), false);
    }

    private void home(){
        mProgressBar3.setProgress(100);
        loadFragment(SplashFragment.this.getActivity().getSupportFragmentManager(),
                new HomeFragment(), false);
        LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        String userId = SharedPrefUtil.getInstance(getActivity()).read(USER_ID, null);
        if (userId != null) {
            viewModelLogin.loggedIn(true);
        }else {
            viewModelLogin.loggedIn(false);
        }
    }
}
