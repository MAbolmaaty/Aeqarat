package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SoftKeyboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);
        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.messages);
        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void menu(){
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }
}
