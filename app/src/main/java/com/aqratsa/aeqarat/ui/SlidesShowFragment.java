package com.aqratsa.aeqarat.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.SlidesAdapter;
import com.aqratsa.aeqarat.models.slides.Slide;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.SlidesViewModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.FIRST_INSTALL;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlidesShowFragment extends Fragment {

    @BindView(R.id.sliderView)
    SliderView mSliderView;
    @BindView(R.id.skip)
    TextView mTextViewSkip;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    private static final String TAG = SlidesShowFragment.class.getSimpleName();

    private List<Slide> mListSlides = new ArrayList<>();
    private SlidesViewModel mViewModelSlides;

    public SlidesShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slides_show, container, false);
        ButterKnife.bind(this, view);

        mSliderView.setIndicatorAnimation(IndicatorAnimations.FILL);
        mViewModelSlides = ViewModelProviders.of(getActivity()).get(SlidesViewModel.class);
        SharedPrefUtil.getInstance(getContext()).write(FIRST_INSTALL, false);
        mSliderView.setCurrentPageListener(currentPosition -> {
            if (currentPosition == 2) {
                mSliderView.setIndicatorSelectedColor(getResources().getColor(R.color.darkGrey));
                mSliderView.setIndicatorUnselectedColor(getResources().getColor(R.color.darkGrey));
                mTextViewSkip.setTextColor(getResources().getColor(R.color.darkGrey));
            } else {
                mSliderView.setIndicatorSelectedColor(Color.WHITE);
                mSliderView.setIndicatorUnselectedColor(Color.WHITE);
                mTextViewSkip.setTextColor(Color.WHITE);
            }
        });

        showSlides();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
    }

    @OnClick(R.id.skip)
    public void skip(){
        mProgressBar.setVisibility(View.VISIBLE);
        mTextViewSkip.setTextColor(getResources().getColor(R.color.grey));
        home();
    }

    private void showSlides() {
        mViewModelSlides.getSlides().observe(this, slidesModelResponse -> {
            mListSlides.add(new Slide(slidesModelResponse.getSlide()[0].getTitle1(),
                    R.drawable.slider_1, slidesModelResponse.getSlide()[0].getLink1()));
            mListSlides.add(new Slide(slidesModelResponse.getSlide()[0].getTitle2(),
                    R.drawable.slider_2, slidesModelResponse.getSlide()[0].getLink2()));
            mListSlides.add(new Slide(slidesModelResponse.getSlide()[0].getTitle3(),
                    R.drawable.slider_3, slidesModelResponse.getSlide()[0].getLink3()));

            SlidesAdapter adapter = new SlidesAdapter(getContext(), mListSlides);
            mSliderView.setSliderAdapter(adapter);
        });
    }

    private void home(){
        loadFragment(SlidesShowFragment.this.getActivity().getSupportFragmentManager(),
                new HomeFragment(), false);
        LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        viewModelLogin.loggedIn(false);
    }
}
