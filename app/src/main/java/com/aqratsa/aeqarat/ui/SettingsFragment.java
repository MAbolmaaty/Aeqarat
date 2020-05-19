package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.SettingsTabsAdapter;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.settings);
        SettingsTabsAdapter adapter = new SettingsTabsAdapter(getContext(), getChildFragmentManager(),
                0);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (locale.equals("ar"))
            mViewPager.setRotation(180);

        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void menu(){
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }
}
