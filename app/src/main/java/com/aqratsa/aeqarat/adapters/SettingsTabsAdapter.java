package com.aqratsa.aeqarat.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.ui.DocumentsAddFragment;
import com.aqratsa.aeqarat.ui.InfoUserFragment;
import com.aqratsa.aeqarat.ui.SettingsAdvancedFragment;
import com.aqratsa.aeqarat.ui.PaymentCardsFragment;

public class SettingsTabsAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;

    @StringRes
    private static final int[] TAB_TITLES =
            new int[]{R.string.info, R.string.documents, R.string.payment_methods, R.string.settings_advanced};

    public SettingsTabsAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return InfoUserFragment.newInstance();
            case 1:
                return DocumentsAddFragment.newInstance();
            case 2:
                return PaymentCardsFragment.newInstance();
            case 3:
                return SettingsAdvancedFragment.newInstance();
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
