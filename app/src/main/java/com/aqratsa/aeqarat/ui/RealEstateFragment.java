package com.aqratsa.aeqarat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.RealEstateTabsAdapter;
import com.aqratsa.aeqarat.adapters.SlidesAdapter;
import com.aqratsa.aeqarat.models.real_estate.Bid;
import com.aqratsa.aeqarat.models.real_estate.RealEstate;
import com.aqratsa.aeqarat.models.real_estate_statuses.Status;
import com.aqratsa.aeqarat.models.requests_user.Request;
import com.aqratsa.aeqarat.models.slides.Slide;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.FavoriteViewModel;
import com.aqratsa.aeqarat.viewmodels.FavoritesViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.OwnerContactViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateCategoriesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateStatusesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.ReportViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestSubmittedViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestsUserViewModel;
import com.aqratsa.aeqarat.viewmodels.UnFavoriteViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.progress;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.AGENT;
import static com.aqratsa.aeqarat.utils.Constants.APPROVED;
import static com.aqratsa.aeqarat.utils.Constants.AUCTIONABLE;
import static com.aqratsa.aeqarat.utils.Constants.CONTACT_OWNER;
import static com.aqratsa.aeqarat.utils.Constants.HOLDER;
import static com.aqratsa.aeqarat.utils.Constants.INDIVIDUAL_OWNER;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_OWNERSHIP;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;
import static com.aqratsa.aeqarat.utils.Constants.REQUEST_RENT;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.TENANT;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;
import static com.aqratsa.aeqarat.utils.Constants.USER_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateFragment extends Fragment {

    private static final String TAG = RealEstateFragment.class.getSimpleName();

    @BindView(R.id.sliderView_realEstate)
    SliderView mSliderView;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    //Expand for Holder
    @BindView(R.id.expandForHolder)
    ImageView mExpandForHolder;
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.image1)
    ImageView mImage1;
    @BindView(R.id.icon1)
    ImageView mIcon1;
    @BindView(R.id.view2)
    View mView2;
    @BindView(R.id.text2)
    TextView mText2;
    @BindView(R.id.image2)
    ImageView mImage2;
    @BindView(R.id.icon2)
    ImageView mIcon2;
    @BindView(R.id.favoriteForHolder)
    ImageView mFavoriteForHolder;
    @BindView(R.id.shareForHolder)
    ImageView mShareForHolder;
    //Expand for Request
    @BindView(R.id.expandForRequest)
    ImageView mExpandForRequest;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.report)
    ImageView mReport;
    @BindView(R.id.favorite)
    ImageView mFavorite;
    @BindView(R.id.view_request)
    View mViewRequest;
    @BindView(R.id.request)
    TextView mRequest;
    @BindView(R.id.imageView_request)
    ImageView mImageViewRequest;
    @BindView(R.id.requestIcon)
    ImageView mRequestIcon;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.addressDetails)
    TextView mAddressDetails;
    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.maxBid)
    TextView mMaxBid;
    @BindView(R.id.viewBid)
    View mViewBid;
    @BindView(R.id.join)
    TextView mJoin;
    @BindView(R.id.expirationDate)
    TextView mExpirationDate;
    @BindView(R.id.bidsCount)
    TextView mBidsCount;
    @BindView(R.id.bids)
    TextView mBids;
    @BindView(R.id.hideMaxBid)
    View mHideMaxBid;
    @BindView(R.id.textView_expirationDate)
    TextView mTextViewExpirationDate;
    @BindView(R.id.constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.shimmer1)
    View mShimmer1;
    @BindView(R.id.shimmer2)
    View mShimmer2;
    @BindView(R.id.shimmer3)
    View mShimmer3;
    @BindView(R.id.category)
    TextView mCategory;
    @BindView(R.id.view_expirationDate)
    View mViewExpirationDate;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Toast mToast;

    //ViewModels
    private ReportViewModel mViewModelReport;
    private FavoriteViewModel mViewModelFavorite;
    private FavoritesViewModel mViewModelFavorites;
    private UnFavoriteViewModel mViewModelUnFavorite;
    private RealEstateStatusesViewModel mViewModelRealEstateStatuses;
    private RealEstateViewModel mViewModelRealEstate;
    private RequestSubmittedViewModel mViewModelSubmittedRequest;
    private OwnerContactViewModel mViewModelContactOwner;
    private RequestsUserViewModel mViewModelUserRequests;
    private RealEstateCategoriesViewModel mViewModelRealEstateCategories;
    private InfoUserViewModel mViewModelUserInfo;

    //RealEstate Info
    private String mOwnerId;
    private String mRealEstateId;
    private String mReportStatus;
    private boolean mAuctionable;
    private String mReportMessage;
    private String mRealEstateLink;
    private String mOwnerPhoneNumber;
    private List<Slide> mListImages = new ArrayList<>();
    private List<Request> mListUserRequests = new ArrayList<>();

    //User
    private String mUserId;
    private String mUserType;
    private String mPrice;
    private String mPriceForMonth;
    private String mPriceFor3Months;
    private String mPriceFor6Months;
    private String mPriceFor12Months;
    private String mLocale;
    private boolean inMyOrders;

    public RealEstateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_estate, container, false);
        ButterKnife.bind(this, view);

        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar")) {
            mViewPager.setRotation(180);
            mBack.setImageResource(R.drawable.ic_arrow_ar);
        }

        //Initialize ViewModels
        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelFavorite = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mViewModelUnFavorite = ViewModelProviders.of(this).get(UnFavoriteViewModel.class);
        mViewModelRealEstateStatuses = ViewModelProviders.of(getActivity()).get(RealEstateStatusesViewModel.class);
        mViewModelSubmittedRequest = ViewModelProviders.of(getActivity()).get(RequestSubmittedViewModel.class);
        mViewModelFavorites = ViewModelProviders.of(getActivity()).get(FavoritesViewModel.class);
        mViewModelRealEstateCategories = ViewModelProviders.of(getActivity()).get(RealEstateCategoriesViewModel.class);
        mViewModelUserInfo = ViewModelProviders.of(getActivity()).get(InfoUserViewModel.class);

        mFavorite.setTranslationX(264);
        mFavorite.setTag(0);
        mFavoriteForHolder.setTag(0);

        mSliderView.setIndicatorAnimation(IndicatorAnimations.FILL);

        mShare.setTranslationX(196);
        mReport.setTranslationX(232);

        RealEstateTabsAdapter realEstateTabsAdapter = new RealEstateTabsAdapter(getContext(), getChildFragmentManager(), 0);
        mViewPager.setAdapter(realEstateTabsAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(1).select();

        LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        mUserId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
        mUserType = "-1";
        if (mUserId == null) {
            viewModelLogin.getUser().observe(this, loginModelResponse -> {
                mUserId = loginModelResponse.getUser().getId();
                mUserType = loginModelResponse.getUser().getType();
            });
        } else {
            mUserType = SharedPrefUtil.getInstance(getContext()).read(USER_TYPE, "-1");
        }
        viewModelLogin.isLoggedIn().observe(this, loggedIn -> {
            if (!loggedIn) {
                mUserId = null;
                mUserType = "-1";
            }
        });

        mViewModelRealEstate.getRealEstateId().observe(this, RealEstateFragment.this::getRealEstate);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Navigation View not allowed
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED, GravityCompat.START);
    }

    @OnClick(R.id.expandForRequest)
    public void requestControls() {
        //Calculate Screen Width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        RealEstateFragment.this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mHideMaxBid.getLayoutParams().width = 2 * displayMetrics.widthPixels;
        mExpandForRequest.setEnabled(false);
        //Close
        if (mAuctionable) {
            mViewBid.setVisibility(View.VISIBLE);
            mMaxBid.setVisibility(View.VISIBLE);
            mHideMaxBid.setVisibility(View.VISIBLE);
            mHideMaxBid.animate().translationX(0).setDuration(296);
            mJoin.animate().translationX(0).setDuration(296);
            mViewRequest.setVisibility(View.INVISIBLE);
            mImageViewRequest.setVisibility(View.INVISIBLE);
        }
        if (mExpandForRequest.getTag().equals(1)) {
            mShare.animate().translationX(196);
            new Handler().postDelayed(() -> {
                mShare.setVisibility(View.GONE);
                mRequestIcon.setVisibility(View.GONE);
            }, 96);
            //
            mReport.animate().translationX(232);
            new Handler().postDelayed(() -> {
                mReport.setVisibility(View.GONE);
            }, 132);
            //
            mFavorite.animate().translationX(264);
            new Handler().postDelayed(() -> {
                mFavorite.setVisibility(View.GONE);
                mViewRequest.setVisibility(View.VISIBLE);
                mImageViewRequest.setVisibility(View.VISIBLE);
            }, 164);
            mExpandForRequest.setTag(0);
            new Handler().postDelayed(() -> {
                mExpandForRequest.animate().translationX(mExpandForRequest.getWidth() / 2f);
            }, 196);

            new Handler().postDelayed(() -> {
                mExpandForRequest.animate().translationX(0);
                mExpandForRequest.setImageResource(R.drawable.ic_circular_dots);
                mExpandForRequest.setEnabled(true);
            }, 408);

        } else {
            //Open
            if (mAuctionable) {
                mHideMaxBid.setVisibility(View.VISIBLE);
                mHideMaxBid.animate().translationX(-displayMetrics.widthPixels).setDuration(496);
                mJoin.animate().translationX(-displayMetrics.widthPixels).setDuration(496);
                mViewRequest.setVisibility(View.INVISIBLE);
                mImageViewRequest.setVisibility(View.INVISIBLE);
                mRequestIcon.setImageResource(R.drawable.ic_auction);
            }
            mExpandForRequest.setTag(1);
            mExpandForRequest.animate().translationX(-(mExpandForRequest.getWidth() / 2f));
            new Handler().postDelayed(() -> {
                mExpandForRequest.animate().translationX(0);
                mExpandForRequest.setImageResource(R.drawable.ic_circular_close);
                mExpandForRequest.setEnabled(true);
            }, 348);

            mShare.animate().translationX(0);
            new Handler().postDelayed(() -> {
                mShare.setVisibility(View.VISIBLE);
                mViewRequest.setVisibility(View.INVISIBLE);
                mImageViewRequest.setVisibility(View.INVISIBLE);
            }, 196);
            //
            mReport.animate().translationX(0);
            new Handler().postDelayed(() -> {
                mReport.setVisibility(View.VISIBLE);
            }, 232);
            //
            mFavorite.animate().translationX(0);
            new Handler().postDelayed(() -> {
                mFavorite.setVisibility(View.VISIBLE);
                mRequestIcon.setVisibility(View.VISIBLE);
                mViewBid.setVisibility(View.INVISIBLE);
                mMaxBid.setVisibility(View.INVISIBLE);
                mHideMaxBid.setVisibility(View.INVISIBLE);
            }, 264);
        }
    }

    @OnClick(R.id.expandForHolder)
    public void holderControls() {
        mExpandForRequest.setEnabled(false);
        if (mExpandForHolder.getTag().equals(1)) {
            //Close
            mExpandForHolder.animate().translationX(-mExpandForHolder.getWidth() / 1.5f).setDuration(308);
            new Handler().postDelayed(() -> {
                mExpandForHolder.animate().translationX(0).setDuration(308);
                mExpandForHolder.setImageResource(R.drawable.ic_circular_dots);
                animateAccounting(0, 308);
                animateRentInfo(0, 308);
                mIcon2.setVisibility(View.GONE);
                mIcon1.setVisibility(View.GONE);
                mFavoriteForHolder.setVisibility(View.GONE);
                mShareForHolder.setVisibility(View.GONE);
                mExpandForRequest.setEnabled(true);
            }, 308);
            mExpandForHolder.setTag(0);
        } else {
            //Open
            mExpandForHolder.animate().translationX(-mExpandForHolder.getWidth() / 1.5f).setDuration(308);
            new Handler().postDelayed(() -> {
                mExpandForHolder.animate().translationX(0).setDuration(308);
                mExpandForHolder.setImageResource(R.drawable.ic_circular_close);
                animateAccounting(-mView2.getWidth() * 3, 308);
                animateRentInfo(-mView2.getWidth() * 3, 308);
                mIcon2.setVisibility(View.VISIBLE);
                mIcon1.setVisibility(View.VISIBLE);
                mFavoriteForHolder.setVisibility(View.VISIBLE);
                mShareForHolder.setVisibility(View.VISIBLE);
                mExpandForRequest.setEnabled(true);
            }, 308);
            mExpandForHolder.setTag(1);
        }
    }

    @OnClick(R.id.report)
    public void report() {
        if (mUserId == null) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getContext(), getString(R.string.login), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }
        BottomSheetDialog dialogReport = new BottomSheetDialog(getContext());
        dialogReport.setContentView(R.layout.dialog_report);
        dialogReport.show();
        ImageView close = dialogReport.getWindow().findViewById(R.id.action1);
        TextView title = dialogReport.getWindow().findViewById(R.id.title);
        AppCompatSpinner realEstateStatuses = dialogReport.getWindow().findViewById(R.id.realEstateStatus);
        EditText reportTitle = dialogReport.getWindow().findViewById(R.id.reportTitle);
        EditText reportMessage = dialogReport.getWindow().findViewById(R.id.reportMessage);
        Button sendReport = dialogReport.getWindow().findViewById(R.id.send);
        ProgressBar progressBar = dialogReport.getWindow().findViewById(R.id.progress);

        close.setImageResource(R.drawable.ic_close);
        close.setOnClickListener(v -> dialogReport.dismiss());
        title.setText(R.string.report);

        List<String> listStatuses = new ArrayList<>();
        mViewModelRealEstateStatuses.getStatuses().observe(this, realEstateStatusesModelResponse -> {
            if (realEstateStatusesModelResponse.getKey().equals(SUCCESS)) {
                listStatuses.clear();
                listStatuses.add(getString(R.string.choose_status));
                for (Status status : realEstateStatusesModelResponse.getStatuses()) {
                    listStatuses.add(status.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        R.layout.list_item_spinner, listStatuses);
                realEstateStatuses.setAdapter(adapter);
            }
        });

        realEstateStatuses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mReportStatus = null;
                        break;
                    case 1:
                        mReportStatus = "1";
                        break;
                    case 2:
                        mReportStatus = "0";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendReport.setOnClickListener(v -> {
            mReportMessage = reportMessage.getText().toString();
            if (TextUtils.isEmpty(mReportMessage)) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getContext(), getString(R.string.enter_message), Toast.LENGTH_SHORT);
                mToast.show();
                return;
            }
            if (mReportStatus == null) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getContext(), getString(R.string.choose_status_first), Toast.LENGTH_SHORT);
                mToast.show();
                return;
            }
            mViewModelReport = ViewModelProviders.of(this).get(ReportViewModel.class);
            mViewModelReport.report(mReportMessage, mUserId, mRealEstateId, mLocale, mReportStatus);
            mViewModelReport.getResult().observe(this, reportModelResponse -> {
                if (reportModelResponse.getMessage() != null) {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getContext(), reportModelResponse.getMessage(), Toast.LENGTH_SHORT);
                    mToast.show();
                    dialogReport.dismiss();
                }
            });
            mViewModelReport.isLoading().observe(this, loading -> {
                progress(loading, progressBar, sendReport);
            });
            mViewModelReport.failure().observe(this, failure -> {
                if (failure) {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
        });
    }

    @OnClick(R.id.view_request)
    public void request() {
        if (mUserId == null) {
            if (mToast != null)
                mToast.cancel();
            mToast = Toast.makeText(getContext(), getString(R.string.login), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }
        if (mViewRequest.getTag().equals(CONTACT_OWNER)) {
            contactOwner();
        } else if (mViewRequest.getTag().equals(REQUEST_OWNERSHIP)) {
            mViewModelRealEstate.setTypeRequest(REQUEST_OWNERSHIP);
            if (inMyOrders) {
                loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                        new RequestSubmittedFragment(), true);
            } else {
                loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                        new RequestFragment(), true);
            }
        } else if (mViewRequest.getTag().equals(REQUEST_RENT)) {
            mViewModelRealEstate.setTypeRequest(REQUEST_RENT);
            if (inMyOrders) {
                loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                        new RequestSubmittedFragment(), true);
            } else {
                loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                        new RequestFragment(), true);
            }
        } else if (mViewRequest.getTag().equals(TENANT)){
            loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                    new RealEstateRequestsUserFragment(), true);
        }
    }

    @OnClick(R.id.share)
    public void share() {
        if (mRealEstateLink != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            intent.putExtra(Intent.EXTRA_TEXT, mRealEstateLink);
            startActivity(Intent.createChooser(intent, "Share URL"));
        } else {
            mToast = Toast.makeText(getContext(), getString(R.string.no_sharable_link), Toast.LENGTH_SHORT);
            if (mToast != null)
                mToast.cancel();
            mToast.show();
        }
    }

    @OnClick(R.id.shareForHolder)
    public void shareForHolder() {
        if (mRealEstateLink != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            intent.putExtra(Intent.EXTRA_TEXT, mRealEstateLink);
            startActivity(Intent.createChooser(intent, "Share URL"));
        } else {
            mToast = Toast.makeText(getContext(), getString(R.string.no_sharable_link), Toast.LENGTH_SHORT);
            if (mToast != null)
                mToast.cancel();
            mToast.show();
        }
    }

    @OnClick(R.id.favorite)
    public void favorite() {
        if (mToast != null)
            mToast.cancel();

        if (mUserId == null) {
            mToast = Toast.makeText(getContext(), getString(R.string.login), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (mFavorite.getTag().equals(0)) {
            mToast = Toast.makeText(getActivity(), R.string.added_to_favorites, Toast.LENGTH_SHORT);
            mToast.show();
            mViewModelFavorite.favorite(mRealEstateId, mUserId, mLocale);
            mViewModelFavorite.isLoading().observe(this, loading -> {
                if (loading) {
                    mFavorite.setEnabled(false);
                } else {
                    mFavorite.setEnabled(true);
                }
            });

            new Handler().postDelayed(() -> {
                mFavorite.setImageResource(R.drawable.ic_liked);
            }, 148);
            mFavorite.setTag(1);
        } else {
            mToast = Toast.makeText(getActivity(), R.string.removed_from_favorites, Toast.LENGTH_SHORT);
            mToast.show();
            mViewModelUnFavorite.unFavorite(mRealEstateId, mUserId, mLocale);
            mViewModelUnFavorite.isLoading().observe(this, loading -> {
                if (loading) {
                    mFavorite.setEnabled(false);
                } else {
                    mFavorite.setEnabled(true);
                }
            });

            new Handler().postDelayed(() -> {
                mFavorite.setImageResource(R.drawable.ic_like);
            }, 148);
            mFavorite.setTag(0);
        }
    }

    @OnClick(R.id.favoriteForHolder)
    public void favoriteForHolder() {
        if (mUserId == null) {
            if (mToast != null)
                mToast.cancel();

            mToast = Toast.makeText(getContext(), getString(R.string.login), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (mFavoriteForHolder.getTag().equals(0)) {
            mToast = Toast.makeText(getActivity(), R.string.added_to_favorites, Toast.LENGTH_SHORT);
            mToast.show();
            mViewModelFavorite.favorite(mRealEstateId, mUserId, mLocale);
            mViewModelFavorite.isLoading().observe(this, loading -> {
                if (loading) {
                    mFavoriteForHolder.setEnabled(false);
                } else {
                    mFavoriteForHolder.setEnabled(true);
                }
            });
            new Handler().postDelayed(() -> {
                mFavoriteForHolder.setImageResource(R.drawable.ic_liked);
            }, 148);
            new Handler().postDelayed(() -> {
            }, 896);
            mFavoriteForHolder.setTag(1);
        } else {
            mToast = Toast.makeText(getActivity(), R.string.removed_from_favorites, Toast.LENGTH_SHORT);
            mToast.show();
            mViewModelUnFavorite.unFavorite(mRealEstateId, mUserId, mLocale);
            mViewModelUnFavorite.isLoading().observe(this, loading -> {
                if (loading) {
                    mFavoriteForHolder.setEnabled(false);
                } else {
                    mFavoriteForHolder.setEnabled(true);
                }
            });
            new Handler().postDelayed(() -> {
                mFavoriteForHolder.setImageResource(R.drawable.ic_like);
            }, 148);
            mFavoriteForHolder.setTag(0);
        }
    }

    @OnClick(R.id.back)
    public void back() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @OnClick(R.id.join)
    public void join() {
        if (mToast != null)
            mToast.cancel();

        if (mUserId == null) {
            mToast = Toast.makeText(getContext(), getString(R.string.login), Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }

        if (mOwnerId.equals(mUserId)) {
            loadFragment(getActivity().getSupportFragmentManager(),
                    new AuctionFragment(), true);
            return;
        }

        mViewModelRealEstate.getRealEstate().observe(RealEstateFragment.this, realEstateModelResponse -> {
            for (Bid bid : realEstateModelResponse.getRealEstate().getBids()) {
                if (bid.getUser_id().equals(mUserId)) {
                    loadFragment(getActivity().getSupportFragmentManager(),
                            new AuctionFragment(), true);
                    return;
                }
            }
            loadFragment(getActivity().getSupportFragmentManager(),
                    new AuctionJoinFragment(), true);
        });
    }

    @OnClick(R.id.view1)
    public void view1() {
        if (mToast != null)
            mToast.cancel();
        if (mText1.getTag().equals(AGENT)){
            loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                    new RealEstateRequestsCurrentFragment(), true);
        } else if (mText1.getTag().equals(HOLDER)){
            mViewModelRealEstate.getRealEstate().observe(this, realEstateModelResponse -> {
                if (realEstateModelResponse.getRealEstate().getService().equals(REAL_ESTATE_RENT) &&
                realEstateModelResponse.getRealEstate().getOwnership().getTenant_id() .equals("0") ||
                        realEstateModelResponse.getRealEstate().getOwnership().getTenant_id() == null){
                    mToast = Toast.makeText(getActivity(), R.string.no_tenant_yet, Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
                loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                        new OccupantFragment(), true);
            });

        } else {
            mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    @OnClick(R.id.view2)
    public void view2() {
        if (mText2.getTag().equals(AGENT)){
            loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                    new AccountingAgentFragment(), true);
        } else if(mText2.getTag().equals(HOLDER)){
            loadFragment(RealEstateFragment.this.getActivity().getSupportFragmentManager(),
                    new AccountingHolderFragment(), true);
        }
    }

    @OnClick(R.id.viewBid)
    public void viewBid(){}

    private void getRealEstate(String id) {
        mViewModelRealEstate.realEstate(id);
        mViewModelRealEstate.getRealEstate().observe(this, realEstateModelResponse -> {
            if (realEstateModelResponse.getKey().equals(SUCCESS)) {
                mShimmer1.setVisibility(View.GONE);
                mShimmer2.setVisibility(View.GONE);
                mShimmer3.setVisibility(View.GONE);
                RealEstate realEstate = realEstateModelResponse.getRealEstate();
                mRealEstateId = realEstate.getId();
                mOwnerId = realEstate.getOwnership().getOwner_id();
                mRealEstateLink = realEstate.getLink();
                mTitle.setText(realEstate.getTitle());
                mAddressDetails.setText(realEstate.getFull_address());
                mPrice = realEstate.getTotal_amount();
                mPriceForMonth = realEstate.getPrice_for_month();
                mPriceFor3Months = realEstate.getPrice_for_3month();
                mPriceFor6Months = realEstate.getPrice_for_6month();
                mPriceFor12Months = realEstate.getPrice_for_12month();

                mViewModelUserRequests = ViewModelProviders.of(getActivity()).get(RequestsUserViewModel.class);
                mViewModelUserRequests.userRequests(mUserId);
                getFavorites(mUserId);
                mViewModelUserRequests.getUserRequests().observe(this, userRequestsModelResponse ->
                {
                    if (userRequestsModelResponse.getKey().equals(SUCCESS)) {
                        mListUserRequests.addAll(Arrays.asList(userRequestsModelResponse.getRequests()));
                        for (Request request : mListUserRequests) {
                            if (request.getAkar_id() != null && request.getAkar_id().equals(mRealEstateId)) {
                                inMyOrders = true;
                                mViewModelSubmittedRequest.setRequestId(request.getId());
                            }
                        }

                        mListImages.clear();
                        for (int i = 0; i < realEstate.getGallery().length; i++) {
                            mListImages.add(new Slide(realEstate.getGallery()[i].getPhoto()));
                        }
                        SlidesAdapter slidesShowAdapter = new SlidesAdapter(getContext(), mListImages);
                        mSliderView.setSliderAdapter(slidesShowAdapter);

                        mViewModelRealEstateCategories.getCategories().observe(this, realEstateCategoriesModelResponse -> {
                            if (realEstateCategoriesModelResponse.getKey().equals(SUCCESS)) {
                                mCategory.setVisibility(View.VISIBLE);
                                mCategory.setText(realEstateCategoriesModelResponse.
                                        getCategories()[Integer.parseInt(realEstateModelResponse.getRealEstate().getCate())].getName());
                            }
                        });

                        //Set Real Estate page depends on Type of the User and the Real Estate
                        if (realEstate.getService().equals(REAL_ESTATE_SALE)) {
                            mStatus.setText(getString(R.string.sale));
                        } else if (realEstate.getService().equals(REAL_ESTATE_RENT)) {
                            mStatus.setText(getString(R.string.rent));
                        }

                        if (mUserType.equals(AGENT)){
                            setRealEstateForAdministrator(realEstateModelResponse.getRealEstate().getService());
                            return;
                        }

                        if (mOwnerId.equals(mUserId) && realEstate.getAuction().equals(AUCTIONABLE)) {
                            setRealEstateForAuctionHolder();
                            if (realEstateModelResponse.getRealEstate().getBids().length > 0) {
                                ArrayList<Bid> bids = new ArrayList<>();
                                double maxBid = 0;
                                for (Bid bid : realEstateModelResponse.getRealEstate().getBids()) {
                                    if (bid.getApprove().equals(APPROVED)) {
                                        bids.add(bid);
                                        if (bid.getPrice() != null && !TextUtils.isEmpty(bid.getPrice()) && Double.parseDouble(bid.getPrice()) > maxBid) {
                                            maxBid = Double.parseDouble(bid.getPrice());
                                        }
                                    }
                                }
                                mBidsCount.setText(String.valueOf(bids.size()));
                                mMaxBid.setText(getString(R.string.max_bid, String.valueOf(maxBid)));
                            }
                            return;
                        }

                        if (mOwnerId.equals(mUserId)) {
                            setRealEstateForHolder(realEstateModelResponse.getRealEstate().getService());
                            return;
                        }

                        if (realEstateModelResponse.getType().equals(INDIVIDUAL_OWNER)) {
                            setRealEstateForIndividualOwner(realEstate.getAuction());
                            return;
                        }

                        if (realEstate.getAuction().equals(AUCTIONABLE)) {
                            setRealEstateForAuction();
                            if (realEstateModelResponse.getRealEstate().getBids().length > 0) {
                                ArrayList<Bid> bids = new ArrayList<>();
                                double maxBid = 0;
                                for (Bid bid : realEstateModelResponse.getRealEstate().getBids()) {
                                    if (bid.getApprove().equals(APPROVED)) {
                                        bids.add(bid);
                                        if (bid.getPrice() != null && !TextUtils.isEmpty(bid.getPrice()) && Double.parseDouble(bid.getPrice()) > maxBid) {
                                            maxBid = Double.parseDouble(bid.getPrice());
                                        }
                                    }
                                }
                                mBidsCount.setText(String.valueOf(bids.size()));
                                mMaxBid.setText(getString(R.string.max_bid, String.valueOf(maxBid)));
                            }
                            return;
                        }

                        if (realEstate.getOwnership().getTenant_id().equals(mUserId)){
                            setRealEstateForTenant();
                            return;
                        }

                        if (realEstate.getService().equals(REAL_ESTATE_SALE)) {
                            setRealEstateForSale();
                            return;
                        }

                        if (realEstate.getService().equals(REAL_ESTATE_RENT)) {
                            setRealEstateForRent();
                        }
                    }
                });
                offsetAnimation();
            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
        mViewModelRealEstate.failure().observe(this, failure -> {
            if (failure) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void getFavorites(String userId) {
        if (userId == null) {
            return;
        }
        mViewModelFavorites.favorites(userId);
        mViewModelFavorites.getFavorites().observe(this, favoritesModelResponse -> {
            if (favoritesModelResponse.getKey().equals(SUCCESS)) {
                for (com.aqratsa.aeqarat.models.favorites.RealEstate favorite : favoritesModelResponse.getRealEstates()) {
                    if (favorite.getAkar_id().equals(mRealEstateId)) {
                        mFavorite.setTag(1);
                        mFavorite.setImageResource(R.drawable.ic_liked);
                        mFavoriteForHolder.setTag(1);
                        mFavoriteForHolder.setImageResource(R.drawable.ic_liked);
                    }
                }
            }
        });
    }

    private void setRealEstateForAdministrator(String status){
        if (status.equals(REAL_ESTATE_SALE)) {
            mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPrice));
        } else if (status.equals(REAL_ESTATE_RENT)) {
            setRentPrice();
        }
        expandForAgent();
    }

    private void setRealEstateForHolder(String status) {
        if (status.equals(REAL_ESTATE_SALE)) {
            mText1.setText(R.string.owner);
            mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPrice));
        } else if (status.equals(REAL_ESTATE_RENT)) {
            mText1.setText(R.string.tenant);
            setRentPrice();
        }
        expandForHolder();
    }

    private void setRealEstateForSale() {
        mStatus.setText(getString(R.string.sale));
        expandForRequest();
        mViewRequest.setTag(REQUEST_OWNERSHIP);
        if (inMyOrders) {
            mRequest.setText(getString(R.string.show_your_request));
        } else {
            mRequest.setText(getString(R.string.request_ownership));
        }
        mImageViewRequest.setImageResource(R.drawable.ic_key);
        mRequestIcon.setImageResource(R.drawable.ic_key);
        mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPrice));
    }

    private void setRealEstateForRent() {
        mStatus.setText(getString(R.string.rent));
        setRentPrice();
        expandForRequest();
        mViewRequest.setTag(REQUEST_RENT);
        if (inMyOrders) {
            mRequest.setText(getString(R.string.show_your_request));
        } else {
            mRequest.setText(getString(R.string.request_rent));
        }
        mImageViewRequest.setImageResource(R.drawable.ic_clock);
        mRequestIcon.setImageResource(R.drawable.ic_clock);
    }

    private void setRealEstateForAuction() {
        expandForRequest();
        showAuctionView();
    }

    private void setRealEstateForAuctionHolder() {
        expandForRequest();
        showAuctionView();
        mReport.setEnabled(false);
        mJoin.setText(R.string.bids);
    }

    private void setRealEstateForIndividualOwner(String auction) {
        if (auction.equals(AUCTIONABLE)) {
            showAuctionView();
            expandForRequest();
        } else {
            expandForRequest();
            mViewRequest.setTag(CONTACT_OWNER);
            mRequest.setText(getString(R.string.owner_contact));
            mImageViewRequest.setImageResource(R.drawable.ic_contact);
            mRequestIcon.setImageResource(R.drawable.ic_contact);
        }
    }

    private void setRealEstateForTenant(){
        expandForRequest();
        mImageViewRequest.setImageResource(R.drawable.ic_info);
        mRequestIcon.setImageResource(R.drawable.ic_info);
        mRequest.setText(getString(R.string.requests));
        mViewRequest.setTag(TENANT);
    }

    private void contactOwner() {
        BottomSheetDialog dialogContactOwner = new BottomSheetDialog(getContext());
        dialogContactOwner.setContentView(R.layout.dialog_contact_owner);
        dialogContactOwner.show();
        ImageView close = dialogContactOwner.getWindow().findViewById(R.id.action1);
        TextView title = dialogContactOwner.getWindow().findViewById(R.id.title);
        TextView phoneNumber = dialogContactOwner.getWindow().findViewById(R.id.phoneNumber);
        EditText message = dialogContactOwner.getWindow().findViewById(R.id.editText_message);
        Button send = dialogContactOwner.getWindow().findViewById(R.id.send);
        ProgressBar progressBar = dialogContactOwner.getWindow().findViewById(R.id.progress);

        close.setImageResource(R.drawable.ic_close);
        close.setOnClickListener(v -> dialogContactOwner.dismiss());
        title.setText(R.string.owner_contact);

        if (mOwnerPhoneNumber != null) {
            phoneNumber.setText(mOwnerPhoneNumber);
        }

        mViewModelContactOwner = ViewModelProviders.of(this).get(OwnerContactViewModel.class);
        send.setOnClickListener(v -> {
            String text = message.getText().toString();
            if (TextUtils.isEmpty(text)) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getContext(), getString(R.string.enter_message), Toast.LENGTH_SHORT);
                mToast.show();
                return;
            }
            mViewModelContactOwner.contactOwner(text, mOwnerId, mLocale);
            mViewModelContactOwner.isLoading().observe(this, loading -> progress(loading, progressBar, send));
            mViewModelContactOwner.getResult().observe(this, contactOwnerModelResponse -> {
                if (contactOwnerModelResponse.getMessage() != null) {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getContext(), contactOwnerModelResponse.getMessage(), Toast.LENGTH_SHORT);
                    mToast.show();
                    dialogContactOwner.dismiss();
                }
            });
            mViewModelContactOwner.failure().observe(this, failure -> {
                if (failure) {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
        });
    }

    private void expandForRequest() {
        mExpandForRequest.setVisibility(View.VISIBLE);
        mViewRequest.setVisibility(View.VISIBLE);
        mRequest.setVisibility(View.VISIBLE);
        mImageViewRequest.setVisibility(View.VISIBLE);
        mExpandForRequest.setTag(0);
    }

    private void expandForHolder() {
        mExpandForHolder.setVisibility(View.VISIBLE);
        mView2.setVisibility(View.VISIBLE);
        mView1.setVisibility(View.VISIBLE);
        mText2.setVisibility(View.VISIBLE);
        mImage2.setVisibility(View.VISIBLE);
        mText1.setVisibility(View.VISIBLE);
        mImage1.setVisibility(View.VISIBLE);
        mText1.setTag(HOLDER);
        mText2.setTag(HOLDER);
        mExpandForHolder.setTag(0);
    }

    private void expandForAgent(){
        mView2.setVisibility(View.VISIBLE);
        mView1.setVisibility(View.VISIBLE);
        mText2.setVisibility(View.VISIBLE);
        mImage2.setVisibility(View.VISIBLE);
        mText1.setVisibility(View.VISIBLE);
        mImage1.setVisibility(View.VISIBLE);
        mText1.setText(R.string.requests);
        mText1.setTag(AGENT);
        mText2.setTag(AGENT);
    }

    private void showAuctionView() {
        mMaxBid.setVisibility(View.VISIBLE);
        mViewBid.setVisibility(View.VISIBLE);
        mJoin.setVisibility(View.VISIBLE);
        mTextViewExpirationDate.setVisibility(View.VISIBLE);
        mViewExpirationDate.setVisibility(View.VISIBLE);
        mExpirationDate.setVisibility(View.VISIBLE);
        mBidsCount.setVisibility(View.VISIBLE);
        mBids.setVisibility(View.VISIBLE);
        mAuctionable = true;
    }

    private void animateAccounting(float translation, long duration) {
        mView2.animate().translationX(translation).setDuration(duration);
        mText2.animate().translationX(translation).setDuration(duration);
        mImage2.animate().translationX(translation).setDuration(duration);
    }

    private void animateRentInfo(float translation, long duration) {
        mView1.animate().translationX(translation).setDuration(duration);
        mText1.animate().translationX(translation).setDuration(duration);
        mImage1.animate().translationX(translation).setDuration(duration);
    }

    private void setRentPrice() {
        if (mPriceForMonth != null) {
            mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPriceForMonth));
        } else if (mPriceFor3Months != null) {
            mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPriceFor3Months));
        } else if (mPriceFor6Months != null) {
            mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPriceFor6Months));
        } else if (mPriceFor12Months != null) {
            mAmount.setText(String.format(getResources().getString(R.string.price_amount), mPriceFor12Months));
        }
    }

    private void offsetAnimation(){
        if (mTitle.getLineCount() + mAddressDetails.getLineCount() > 6) {
            int originalRange = mAppBarLayout.getTotalScrollRange();
            int infoHeight = ((mTitle.getLineCount() * mTitle.getLineHeight()) +
                    (mAddressDetails.getLineCount() * mAddressDetails.getLineHeight())) -
                    ((int)(mView2.getLayoutParams().height * 1.5f));

            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    int offset = (originalRange + i) / 100;
                    if (-i > 100) {
                        mTitle.animate().alpha(offset / 10f);
                        mAddressDetails.animate().alpha(offset / 10f);
                    } else {
                        mTitle.animate().alpha(1);
                        mAddressDetails.animate().alpha(1);
                    }

                    if (originalRange + i < 100) {
                        translateViewAccounting(-infoHeight, true);
                        translateViewClientInfo(-infoHeight, true);
                        translateExpandForHolder(-infoHeight, true);
                        translateViewsAuction(-infoHeight, true);
                        translateExpandForRequest(-infoHeight, true);
                    } else {
                        translateViewAccounting(0, true);
                        translateViewClientInfo(0, true);
                        translateExpandForHolder(0, true);
                        translateViewsAuction(0, true);
                        translateExpandForRequest(0, true);
                    }

                    if (originalRange == -i){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mConstraintLayout.setVisibility(View.GONE);
                            }
                        }, 296);
                    }

                    if (i == 0){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mConstraintLayout.setVisibility(View.VISIBLE);
                            }
                        }, 296);
                    }
                }
            });
        }
    }

    private void translateViewAccounting(int value, boolean animation){
        if (animation){
            mView2.animate().translationY(value);
            mText2.animate().translationY(value);
            mImage2.animate().translationY(value);

        } else {
            mView2.setTranslationY(value);
            mText2.setTranslationY(value);
            mImage2.setTranslationY(value);
        }
    }

    private void translateViewClientInfo(int value, boolean animation){
        if (animation){
            mView1.animate().translationY(value);
            mText1.animate().translationY(value);
            mImage1.animate().translationY(value);
        } else {
            mView1.setTranslationY(value);
            mText1.setTranslationY(value);
            mImage1.setTranslationY(value);
        }
    }

    private void translateExpandForRequest(int value, boolean animation){
        if (animation){
            mExpandForRequest.animate().translationY(value);
            mViewRequest.animate().translationY(value);
            mRequest.animate().translationY(value);
            mImageViewRequest.animate().translationY(value);
            mFavorite.animate().translationY(value);
            mReport.animate().translationY(value);
            mShare.animate().translationY(value);
            mViewRequest.animate().translationY(value);
            mRequestIcon.animate().translationY(value);
        } else {
            mExpandForRequest.setTranslationY(value);
            mViewRequest.setTranslationY(value);
            mRequest.setTranslationY(value);
            mImageViewRequest.setTranslationY(value);
            mFavorite.setTranslationY(value);
            mReport.setTranslationY(value);
            mShare.setTranslationY(value);
            mViewRequest.setTranslationY(value);
            mRequestIcon.setTranslationY(value);
        }
    }

    private void translateExpandForHolder(int value, boolean animation){
        if (animation){
            mExpandForHolder.animate().translationY(value);
            mFavoriteForHolder.animate().translationY(value);
            mShareForHolder.animate().translationY(value);
            mIcon1.animate().translationY(value);
            mIcon2.animate().translationY(value);
        } else {
            mExpandForHolder.setTranslationY(value);
            mFavoriteForHolder.setTranslationY(value);
            mShareForHolder.setTranslationY(value);
            mIcon1.setTranslationY(value);
            mIcon2.setTranslationY(value);
        }
    }

    private void translateViewsAuction(int value, boolean animation){
        if (animation){
            mHideMaxBid.animate().translationY(value);
            mMaxBid.animate().translationY(value);
            mViewBid.animate().translationY(value);
            mJoin.animate().translationY(value);
            mBids.animate().translationY(value);
            mBidsCount.animate().translationY(value);
            mTextViewExpirationDate.animate().translationY(value);
            mExpirationDate.animate().translationY(value);
            mViewExpirationDate.animate().translationY(value);
        } else {
            mJoin.setTranslationY(value);
            mHideMaxBid.setTranslationY(value);
            mBids.setTranslationY(value);
            mBidsCount.setTranslationY(value);
            mTextViewExpirationDate.setTranslationY(value);
            mExpirationDate.setTranslationY(value);
            mViewExpirationDate.setTranslationY(value);
            mMaxBid.setTranslationY(value);
            mViewBid.setTranslationY(value);
        }
    }
}
