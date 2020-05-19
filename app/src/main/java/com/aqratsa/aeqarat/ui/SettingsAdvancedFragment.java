package com.aqratsa.aeqarat.ui;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsAdvancedFragment extends Fragment {

    private static final String TAG = SettingsAdvancedFragment.class.getSimpleName();

    @BindView(R.id.language)
    TextView mLanguage;
    @BindView(R.id.selectedLanguage)
    TextView mSelectedLanguage;

    private BottomSheetDialog mDialogLanguage;

    public SettingsAdvancedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_advanced, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar")) {
            view.setRotation(-180);
            mSelectedLanguage.setText(R.string.arabic);
        } else {
            mSelectedLanguage.setText(R.string.english);
        }
        mDialogLanguage = new BottomSheetDialog(getActivity());
        mDialogLanguage.setContentView(R.layout.dialog_select_language);
        setLanguageDialog();
        return view;
    }

    @OnClick({R.id.language, R.id.selectedLanguage})
    public void setLanguage(){
        mDialogLanguage.show();
    }

    private void setLanguageDialog(){
        Window dialog = mDialogLanguage.getWindow();
        ImageView close = dialog.findViewById(R.id.action1);
        TextView title = dialog.findViewById(R.id.title);
        TextView english = dialog.findViewById(R.id.english);
        TextView arabic = dialog.findViewById(R.id.arabic);
        close.setImageResource(R.drawable.ic_close);
        close.setOnClickListener(v -> mDialogLanguage.cancel());
        title.setText(R.string.select_language);
        english.setOnClickListener(v -> {
            mDialogLanguage.cancel();
            SharedPrefUtil.getInstance(getActivity()).write(LOCALE, "en");
            mSelectedLanguage.setText(R.string.english);
            getActivity().recreate();
        });
        arabic.setOnClickListener(v -> {
            mDialogLanguage.cancel();
            SharedPrefUtil.getInstance(getActivity()).write(LOCALE, "ar");
            mSelectedLanguage.setText(R.string.arabic);
            getActivity().recreate();
        });
    }

    public static SettingsAdvancedFragment newInstance(){
        return new SettingsAdvancedFragment();
    }
}
