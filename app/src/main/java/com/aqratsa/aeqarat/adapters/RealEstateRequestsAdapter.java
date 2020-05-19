package com.aqratsa.aeqarat.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.ui.RealEstateRequestsMaintenanceFragment;
import com.aqratsa.aeqarat.ui.RealEstateRequestsTerminationFragment;

public class RealEstateRequestsAdapter extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLES =
            new int[] {R.string.maintenance, R.string.termination};

    private final Context mContext;

    public RealEstateRequestsAdapter(Context context,
                                     @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RealEstateRequestsMaintenanceFragment.newInstance();
            case 1:
                return RealEstateRequestsTerminationFragment.newInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
