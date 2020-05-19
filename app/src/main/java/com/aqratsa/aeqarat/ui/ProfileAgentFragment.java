package com.aqratsa.aeqarat.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqratsa.aeqarat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileAgentFragment extends Fragment {

    public ProfileAgentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_agent, container, false);
    }
}
