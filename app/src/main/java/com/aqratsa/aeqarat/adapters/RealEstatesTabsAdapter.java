package com.aqratsa.aeqarat.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.ui.RealEstatesOwnedFragment;
import com.aqratsa.aeqarat.ui.RealEstatesRentedFragment;

public class RealEstatesTabsAdapter extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLES =
            new int[] {R.string.real_estates_owned, R.string.rented_real_estates};

    private final Context mContext;

    public RealEstatesTabsAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RealEstatesOwnedFragment.newInstance();
            case 1:
                return RealEstatesRentedFragment.newInstance();
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
