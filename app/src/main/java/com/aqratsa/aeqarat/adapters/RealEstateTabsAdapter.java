package com.aqratsa.aeqarat.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.ui.DescriptionFragment;
import com.aqratsa.aeqarat.ui.DetailsFragment;
import com.aqratsa.aeqarat.ui.ReviewsFragment;

public class RealEstateTabsAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;

    @StringRes
    private static final int[] TAB_TITLES =
            new int[]{R.string.description, R.string.details, R.string.reviews};

    public RealEstateTabsAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DescriptionFragment.newInstance();
            case 1:
                return DetailsFragment.newInstance();
            case 2:
                return ReviewsFragment.newInstance();
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
