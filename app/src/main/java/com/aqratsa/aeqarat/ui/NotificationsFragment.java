package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.NotificationsAdapter;
import com.aqratsa.aeqarat.models.notifications.Notification;
import com.aqratsa.aeqarat.models.notifications.NotificationsModelResponse;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.NotificationsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private static final String TAG = NotificationsFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;
    @BindView(R.id.noNotifications)
    TextView mNoNotifications;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    private Toast mToast;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);
        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.notifications);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        String userId = SharedPrefUtil.getInstance(getActivity()).read(USER_ID, null);
        if (userId == null) {
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.getUser().observe(this, loginModelResponse -> {
                loadNotifications(loginModelResponse.getUser().getId());
            });
        } else {
            loadNotifications(userId);
        }
        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void menu(){
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    private void loadNotifications(String userId){
        if (mToast != null)
            mToast.cancel();
        NotificationsViewModel viewModelNotifications =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        viewModelNotifications.notifications(userId);
        viewModelNotifications.getNotifications().observe(this, notificationsModelResponse -> {
            if (notificationsModelResponse.getNotifications() != null){
                if (notificationsModelResponse.getNotifications().length < 1){
                    mNoNotifications.setVisibility(View.VISIBLE);
                } else {
                    mNoNotifications.setVisibility(View.GONE);
                }
                ArrayList<Notification> notifications =
                        new ArrayList<>(Arrays.asList(notificationsModelResponse.getNotifications()));
                NotificationsAdapter adapter = new NotificationsAdapter(getActivity(),
                        notifications);
                mRecyclerView.setAdapter(adapter);
            } else {
                mNoNotifications.setVisibility(View.VISIBLE);
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
        viewModelNotifications.failure().observe(this, failed -> {
            if (failed){
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
                mNoNotifications.setVisibility(View.VISIBLE);
            }
        });
        viewModelNotifications.isLoading().observe(this, loading -> {
            if (loading){
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.GONE);
            }
        });
    }
}
