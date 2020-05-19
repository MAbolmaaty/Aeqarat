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
import com.aqratsa.aeqarat.adapters.FavoritesAdapter;
import com.aqratsa.aeqarat.models.favorites.RealEstate;
import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.FavoritesViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.UnFavoriteViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private static final String TAG = FavoritesFragment.class.getSimpleName();

    @BindView(R.id.toolbarAction1)
    ImageView mMenu;
    @BindView(R.id.toolbarTitle2)
    TextView mTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.noRealEstates)
    TextView mNoRealEstates;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private FavoritesViewModel mViewModelFavorites;
    private RealEstateViewModel mViewModelRealEstate;
    private String mUserId;
    private FavoritesAdapter mAdapter;
    private List<RealEstate> mListFavorites;
    private UnFavoriteViewModel mViewModelUnFavorite;
    private String mLocale;
    private Toast mToast;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        mMenu.setImageResource(R.drawable.ic_menu);
        mTitle.setText(R.string.favorites);

        mViewModelFavorites = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelUnFavorite = ViewModelProviders.of(this).get(UnFavoriteViewModel.class);

        mListFavorites = new ArrayList<>();
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new FavoritesAdapter(getContext(), mListFavorites, position -> {
            mViewModelRealEstate.setRealEstateId(mListFavorites.get(position).getAkar_id());
            loadFragment(FavoritesFragment.this.getActivity().getSupportFragmentManager(),
                    new RealEstateFragment(), true);
        }, position -> {
            mViewModelUnFavorite.unFavorite(mListFavorites.get(position).getAkar_id(), mUserId, mLocale);
            mViewModelUnFavorite.getResult().observe(FavoritesFragment.this, unFavoriteModelResponse -> {
                if (unFavoriteModelResponse.getKey().equals(SUCCESS)) {
                    mListFavorites.remove(position);
                    if (mListFavorites.size() < 1) {
                        mNoRealEstates.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
            mViewModelUnFavorite.isLoading().observe(FavoritesFragment.this, loading -> {
                if (loading) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        });
        mRecyclerView.setAdapter(mAdapter);

        mUserId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
        if (mUserId == null){
            LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
            viewModelLogin.getUser().observe(this, loginModelResponse -> {
                mUserId = loginModelResponse.getUser().getId();
                getFavorites();
            });
        } else {
            getFavorites();
        }

        return view;
    }

    @OnClick(R.id.toolbarAction1)
    public void menu(){
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    private void getFavorites(){
        Log.i(TAG, "get favorites");
        mViewModelFavorites.favorites(mUserId);
        mViewModelFavorites.getFavorites().observe(this, favoritesModelResponse -> {
            if (favoritesModelResponse.getKey().equals(SUCCESS)){
                mListFavorites.clear();
                mListFavorites.addAll(Arrays.asList(favoritesModelResponse.getRealEstates()));
                if (mListFavorites.size() < 1){
                    mNoRealEstates.setVisibility(View.VISIBLE);
                } else {
                    mNoRealEstates.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();

            }
        });
        mViewModelFavorites.isLoading().observe(this, loading -> {
            if (loading){
                mProgressBar.setVisibility(View.VISIBLE);
            }else {
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mViewModelFavorites.failure().observe(this, failure -> {
            if (failure){
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
}
