package com.aqratsa.aeqarat.ui;


import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.real_estate_categories.Category;
import com.aqratsa.aeqarat.models.real_estate_statuses.Status;
import com.aqratsa.aeqarat.models.real_estates.response.RealEstate;
import com.aqratsa.aeqarat.models.search.request.SearchModelRequest;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.DistrictsViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateCategoriesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateStatusesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstatesViewModel;
import com.aqratsa.aeqarat.viewmodels.RegionsViewModel;
import com.aqratsa.aeqarat.viewmodels.RequestsUserViewModel;
import com.aqratsa.aeqarat.viewmodels.SearchViewModel;
import com.github.guilhe.views.SeekBarRangedView;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.ui.MainActivity.sDrawerLayout;
import static com.aqratsa.aeqarat.utils.Constants.AUCTIONABLE;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.imageView_sale)
    ImageView mImageViewSale;
    @BindView(R.id.imageView_rent)
    ImageView mImageViewRent;
    @BindView(R.id.imageView_auctions)
    ImageView mImageViewAuctions;
    @BindView(R.id.sale)
    TextView mSale;
    @BindView(R.id.rent)
    TextView mRent;
    @BindView(R.id.auctions)
    TextView mAuctions;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.menu)
    ImageView mMenu;
    @BindView(R.id.filter)
    ImageView mFilter;

    private static final String TAG = HomeFragment.class.getSimpleName();

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private RealEstatesViewModel mViewModelRealEstates;
    private RealEstateViewModel mViewModelRealEstate;
    private SearchViewModel mViewModelSearch;
    private final static int REQUEST_CHECk_SETTINGS = 7007;
    private final static int ACCESS_LOCATION_REQUEST_PERMISSION = 7017;
    private static List<Marker> mListSaleMarkers = new ArrayList<>();
    private static List<Marker> mListRentMarkers = new ArrayList<>();
    private static List<Marker> mListAuctionMarkers = new ArrayList<>();
    private BottomSheetDialog mDialogFilter;
    private Toast mToast;
    private String mLocale;
    private String mUserId;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        mUserId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
        if (mUserId != null) {
            RequestsUserViewModel viewModelUserRequests = ViewModelProviders.of(getActivity()).get(RequestsUserViewModel.class);
            viewModelUserRequests.userRequests(mUserId);
        }
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        mViewModelRealEstates = ViewModelProviders.of(getActivity()).get(RealEstatesViewModel.class);
        mViewModelSearch = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        mViewModelRealEstates.realEstates("1", "1", "0");
        mViewModelRealEstates.startMapFragment(true);

        //Real Estate Details
        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        getRealEstates();

        mImageViewSale.setTag(1);
        mImageViewRent.setTag(1);
        mImageViewAuctions.setTag(0);

        mDialogFilter = new BottomSheetDialog(getContext());
        mDialogFilter.setContentView(R.layout.dialog_filter);
        setDialogFilter();

        //SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setFocusable(false);
        mSearchView.findViewById(androidx.appcompat.R.id.search_plate).setBackgroundColor(Color.TRANSPARENT);
        ((LinearLayout) mSearchView.findViewById(R.id.search_voice_btn).getParent()).setBackgroundColor(Color.TRANSPARENT);

        if (mLocale.equals("ar")) {
            mSearchView.findViewById(androidx.appcompat.R.id.search_plate).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            mSearchView.findViewById(androidx.appcompat.R.id.search_close_btn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            mSearchView.findViewById(androidx.appcompat.R.id.search_go_btn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            mSearchView.findViewById(androidx.appcompat.R.id.search_go_btn).setRotation(180);
            mSearchView.findViewById(androidx.appcompat.R.id.search_voice_btn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            mSearchView.findViewById(androidx.appcompat.R.id.search_mag_icon).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED, GravityCompat.START);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_LOCATION_REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            createLocationRequest();
        } else {
            Toast.makeText(getContext(), getString(R.string.location_access_denied), Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0, (mMenu.getLayoutParams().height*2), 0, 0);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.maps_style_json));
        mMap.setOnMarkerClickListener(marker -> {
            mViewModelRealEstate.setRealEstateId(marker.getTag().toString());
            HomeFragment.this.getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RealEstateFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        });
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            createLocationRequest();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_PERMISSION);
        }
    }

    @OnClick(R.id.menu)
    public void menu() {
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    @OnClick(R.id.filter)
    public void openFilter() {
        mDialogFilter.show();
    }

    @OnClick(R.id.imageView_sale)
    public void controlSaleRealEstates() {
        if (mImageViewSale.getTag().equals(1)) {
            mImageViewSale.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mSale.setTextColor(getResources().getColor(R.color.darkGrey));
            saleMarkersVisibility(false);
            mImageViewSale.setTag(0);
        } else {
            mImageViewSale.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.darkGrey));
            mSale.setTextColor(getResources().getColor(R.color.white));
            mImageViewAuctions.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mAuctions.setTextColor(getResources().getColor(R.color.darkGrey));
            saleMarkersVisibility(true);
            mImageViewSale.setTag(1);
            mImageViewAuctions.setTag(0);
            auctionMarkersVisibility(false);
        }
    }

    @OnClick(R.id.imageView_rent)
    public void controlRentRealEstates() {
        if (mImageViewRent.getTag().equals(1)) {
            mImageViewRent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mRent.setTextColor(getResources().getColor(R.color.darkGrey));
            rentMarkersVisibility(false);
            mImageViewRent.setTag(0);
        } else {
            mImageViewRent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.darkGrey));
            mRent.setTextColor(getResources().getColor(R.color.white));
            rentMarkersVisibility(true);
            mImageViewAuctions.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mAuctions.setTextColor(getResources().getColor(R.color.darkGrey));
            mImageViewRent.setTag(1);
            mImageViewAuctions.setTag(0);
            auctionMarkersVisibility(false);
        }
    }

    @OnClick(R.id.imageView_auctions)
    public void controlAuctionRealEstates() {
        if (mImageViewAuctions.getTag().equals(1)) {
            mImageViewAuctions.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mAuctions.setTextColor(getResources().getColor(R.color.darkGrey));
            mImageViewAuctions.setTag(0);
            auctionMarkersVisibility(false);
        } else {
            mImageViewAuctions.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.darkGrey));
            mAuctions.setTextColor(getResources().getColor(R.color.white));
            mImageViewAuctions.setTag(1);
            auctionMarkersVisibility(true);
            mImageViewRent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mRent.setTextColor(getResources().getColor(R.color.darkGrey));
            rentMarkersVisibility(false);
            mImageViewRent.setTag(0);
            mImageViewSale.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
            mSale.setTextColor(getResources().getColor(R.color.darkGrey));
            saleMarkersVisibility(false);
            mImageViewSale.setTag(0);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), locationSettingsResponse -> {
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                        mMap.getUiSettings().setCompassEnabled(true);
                        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
                    }

                }

            };

            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper());
        });

        task.addOnFailureListener(getActivity(), e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(getActivity(), REQUEST_CHECk_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {

                }
            }
        });
    }

    private void getRealEstates() {
        //Customized Auction Marker
        BitmapDrawable drawableAuction = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_marker_auction);
        Bitmap bitmapAuction = drawableAuction.getBitmap();
        Bitmap iconAuction = Bitmap.createScaledBitmap(bitmapAuction,
                (int) (drawableAuction.getIntrinsicWidth()/1.2),
                (int)(drawableAuction.getIntrinsicHeight()/1.2), false);
        //Customized Sale Marker
        BitmapDrawable drawableSale = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_marker_sale);
        Bitmap bitmapSale = drawableSale.getBitmap();
        Bitmap iconSale = Bitmap.createScaledBitmap(bitmapSale,
                (int) (drawableSale.getIntrinsicWidth()/1.2),
                (int)(drawableSale.getIntrinsicHeight()/1.2), false);
        //Customized Rent Marker
        BitmapDrawable drawableRent = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_marker_rent);
        Bitmap bitmapRent = drawableRent.getBitmap();
        Bitmap iconRent = Bitmap.createScaledBitmap(bitmapRent,
                (int) (drawableRent.getIntrinsicWidth()/1.2),
                (int)(drawableRent.getIntrinsicHeight()/1.2), false);

        mViewModelRealEstates.getRealEstates().observe(this, realEstatesModelResponse -> {
            if (realEstatesModelResponse.getKey().equals(SUCCESS)
                    && realEstatesModelResponse.getRealEstates().length > 0) {
                Marker marker;
                int searchLowestArea = Integer.parseInt(realEstatesModelResponse.getRealEstates()[0].getApartment_space());
                int searchHighestArea = Integer.parseInt(realEstatesModelResponse.getRealEstates()[0].getApartment_space());
                for (int i = 0; i < realEstatesModelResponse.getRealEstates().length; i++) {
                    if (TextUtils.isEmpty(realEstatesModelResponse.getRealEstates()[i].getLatitude())) {
                        continue;
                    }
                    LatLng latLng = new LatLng(Double.parseDouble(realEstatesModelResponse.getRealEstates()[i].getLatitude()),
                            Double.parseDouble(realEstatesModelResponse.getRealEstates()[i].getLongitude()));
                    if (realEstatesModelResponse.getRealEstates()[i].getAuction().equals(AUCTIONABLE)) {
                        marker = mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconAuction)));
                        marker.setTag(realEstatesModelResponse.getRealEstates()[i].getId());
                        marker.setVisible(false);
                        mListAuctionMarkers.add(marker);
                    }
                    if (realEstatesModelResponse.getRealEstates()[i].getService().equals(REAL_ESTATE_SALE)) {
                        marker = mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconSale)));
                        marker.setTag(realEstatesModelResponse.getRealEstates()[i].getId());
                        mListSaleMarkers.add(marker);
                    } else if (realEstatesModelResponse.getRealEstates()[i].getService().equals(REAL_ESTATE_RENT)) {
                        marker = mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconRent)));
                        marker.setTag(realEstatesModelResponse.getRealEstates()[i].getId());
                        mListRentMarkers.add(marker);
                    }

                    if (Integer.parseInt(realEstatesModelResponse.getRealEstates()[i].getApartment_space()) < searchLowestArea &&
                            Integer.parseInt(realEstatesModelResponse.getRealEstates()[i].getApartment_space()) > 0) {
                        searchLowestArea = Integer.parseInt(realEstatesModelResponse.getRealEstates()[i].getApartment_space());
                    }

                    if (Integer.parseInt(realEstatesModelResponse.getRealEstates()[i].getApartment_space()) > searchHighestArea) {
                        searchHighestArea = Integer.parseInt(realEstatesModelResponse.getRealEstates()[i].getApartment_space());
                    }

                }
            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();

            }
        });
        mViewModelRealEstates.failure().observe(this, failure -> {
            if (failure) {
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void saleMarkersVisibility(boolean visibility) {
        if (visibility && mListSaleMarkers.size() > 0) {
            for (int i = 0; i < mListSaleMarkers.size(); i++) {
                mListSaleMarkers.get(i).setVisible(true);
            }
        } else if (!visibility && mListSaleMarkers.size() > 0) {
            for (int i = 0; i < mListSaleMarkers.size(); i++) {
                mListSaleMarkers.get(i).setVisible(false);
            }
        }
    }

    private void rentMarkersVisibility(boolean visibility) {
        if (visibility && mListRentMarkers.size() > 0) {
            for (int i = 0; i < mListRentMarkers.size(); i++) {
                mListRentMarkers.get(i).setVisible(true);
            }
        } else if (!visibility && mListRentMarkers.size() > 0) {
            for (int i = 0; i < mListRentMarkers.size(); i++) {
                mListRentMarkers.get(i).setVisible(false);
            }
        }
    }

    private void auctionMarkersVisibility(boolean visibility) {
        if (visibility && mListAuctionMarkers.size() > 0) {
            for (int i = 0; i < mListAuctionMarkers.size(); i++) {
                mListAuctionMarkers.get(i).setVisible(true);
            }
        } else if (!visibility && mListAuctionMarkers.size() > 0) {
            for (int i = 0; i < mListAuctionMarkers.size(); i++) {
                mListAuctionMarkers.get(i).setVisible(false);
            }
        }
    }

    private void setDialogFilter() {
        Window dialog = mDialogFilter.getWindow();
        ScrollView scrollView = dialog.findViewById(R.id.scrollView);
        ImageView close = dialog.findViewById(R.id.action1);
        TextView title = dialog.findViewById(R.id.title);
        AppCompatSpinner statuses = dialog.findViewById(R.id.realEstateStatuses);
        ImageView arrowStatuses = dialog.findViewById(R.id.arrowStatuses);
        AppCompatSpinner categories = dialog.findViewById(R.id.realEstateCategories);
        ImageView arrowCategories = dialog.findViewById(R.id.arrowCategories);
        AppCompatSpinner regions = dialog.findViewById(R.id.regions);
        ImageView arrowRegions = dialog.findViewById(R.id.arrowRegions);
        AppCompatSpinner districts = dialog.findViewById(R.id.districts);
        ImageView arrowDistricts = dialog.findViewById(R.id.arrowDistricts);
        EditText realEstateAge = dialog.findViewById(R.id.realEstateAge);
        SeekBarRangedView seekBarPrice = dialog.findViewById(R.id.seekBar_price);
        TextView minPrice = dialog.findViewById(R.id.minPrice);
        TextView maxPrice = dialog.findViewById(R.id.maxPrice);
        SeekBarRangedView seekBarArea = dialog.findViewById(R.id.seekBar_area);
        TextView minArea = dialog.findViewById(R.id.minArea);
        TextView maxArea = dialog.findViewById(R.id.maxArea);
        Button search = dialog.findViewById(R.id.search);
        ImageView reset = dialog.findViewById(R.id.reset);

        title.setText(R.string.search_advanced);
        close.setImageResource(R.drawable.ic_close);
        close.setOnClickListener(v -> mDialogFilter.cancel());

        mDialogFilter.setOnDismissListener(dialog12 -> scrollView.scrollTo(0, 0));

        setRealEstateStatuses(statuses);
        setRealEstateCategories(categories);
        setRegions(regions);
        setDistricts(districts);

        if (mLocale.equals("ar")) {
            seekBarPrice.setRotation(180);
            seekBarArea.setRotation(180);
        }

        mViewModelRealEstates.getRealEstates().observe(this, realEstatesModelResponse -> {
            if (realEstatesModelResponse.getKey().equals(SUCCESS)) {
                int minPriceValue = 0, maxPriceValue = 0, minAreaValue = 0, maxAreaValue = 0;
                for (RealEstate realEstate : realEstatesModelResponse.getRealEstates()) {
                    if (realEstate.getTotal_amount() != null && realEstate.getService().equals(REAL_ESTATE_SALE)) {
                        try {
                            minPriceValue = Integer.parseInt(realEstate.getTotal_amount());
                            maxPriceValue = Integer.parseInt(realEstate.getTotal_amount());
                            break;
                        } catch (ClassCastException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                for (RealEstate realEstate : realEstatesModelResponse.getRealEstates()) {
                    if (realEstate.getApartment_space() != null) {
                        try {
                            minAreaValue = Integer.parseInt(realEstate.getApartment_space());
                            maxAreaValue = Integer.parseInt(realEstate.getApartment_space());
                            break;
                        } catch (ClassCastException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                int currPriceValue, currAreaValue;
                for (RealEstate realEstate : realEstatesModelResponse.getRealEstates()) {
                    if (realEstate.getTotal_amount() != null && realEstate.getService().equals(REAL_ESTATE_SALE)) {
                        try {
                            currPriceValue = Integer.parseInt(realEstate.getTotal_amount());
                            if (currPriceValue < minPriceValue) {
                                minPriceValue = currPriceValue;
                            }

                            if (currPriceValue > maxPriceValue) {
                                maxPriceValue = currPriceValue;
                            }
                        } catch (ClassCastException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (realEstate.getApartment_space() != null) {
                        try {
                            currAreaValue = Integer.parseInt(realEstate.getApartment_space());
                            if (currAreaValue < minAreaValue) {
                                minAreaValue = currAreaValue;
                            }

                            if (currAreaValue > maxAreaValue) {
                                maxAreaValue = currAreaValue;
                            }
                        } catch (ClassCastException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                minPrice.setText(getString(R.string.price_amount, String.valueOf(minPriceValue)));
                maxPrice.setText(getString(R.string.price_amount, String.valueOf(maxPriceValue)));
                minArea.setText(getString(R.string.value_area, String.valueOf(minAreaValue)));
                maxArea.setText(getString(R.string.value_area, String.valueOf(maxAreaValue)));
                seekBarPrice.setMinValue(minPriceValue);
                seekBarPrice.setMaxValue(maxPriceValue);
                seekBarArea.setMinValue(minAreaValue);
                seekBarArea.setMaxValue(maxAreaValue);
                seekBarPrice.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
                    @Override
                    public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

                    }

                    @Override
                    public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
                        minPrice.setText(getString(R.string.price_amount, String.valueOf((int) minValue)));
                        maxPrice.setText(getString(R.string.price_amount, String.valueOf((int) maxValue)));
                    }
                });
                seekBarArea.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
                    @Override
                    public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {

                    }

                    @Override
                    public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {
                        minArea.setText(getString(R.string.value_area, String.valueOf((int) minValue)));
                        maxArea.setText(getString(R.string.value_area, String.valueOf((int) maxValue)));
                    }
                });
            }

            search.setOnClickListener(v -> {
                String status = null,
                        category = null,
                        region = null,
                        district = null,
                        age = null;

                if (statuses.getSelectedItemPosition() > 0) {
                    if (statuses.getSelectedItemPosition() == 1) {
                        status = REAL_ESTATE_SALE;
                    } else if (statuses.getSelectedItemPosition() == 2) {
                        status = REAL_ESTATE_RENT;
                    }
                }

                if (categories.getSelectedItemPosition() > 0) {
                    category = String.valueOf(categories.getSelectedItemPosition() - 1);
                }

                if (regions.getSelectedItemPosition() > 0) {
                    region = regions.getSelectedItem().toString();
                }

                if (districts.getSelectedItemPosition() > 0) {
                    district = districts.getSelectedItem().toString();
                }

                age = realEstateAge.getText().toString();

                if (status != null && status.equals(REAL_ESTATE_SALE)) {
                    mViewModelSearch.setParameters(new SearchModelRequest(category, status,
                            String.valueOf((int) seekBarPrice.getSelectedMinValue()),
                            String.valueOf((int) seekBarPrice.getSelectedMaxValue()),
                            mSearchView.getQuery().toString(), region, district, age,
                            String.valueOf((int) seekBarArea.getSelectedMinValue()),
                            String.valueOf((int) seekBarArea.getSelectedMaxValue()),
                            regions.getSelectedItemPosition(),
                            districts.getSelectedItemPosition()));
                } else {
                    mViewModelSearch.setParameters(new SearchModelRequest(category, status,
                            null, null,
                            mSearchView.getQuery().toString(), region, district, age,
                            String.valueOf((int) seekBarArea.getSelectedMinValue()),
                            String.valueOf((int) seekBarArea.getSelectedMaxValue()),
                            regions.getSelectedItemPosition(),
                            districts.getSelectedItemPosition()));
                }
                loadFragment(HomeFragment.this.getActivity().getSupportFragmentManager(),
                        new SearchFragment(), false);
                mDialogFilter.dismiss();
            });
        });

        reset.setOnClickListener(v -> {
            reset.setEnabled(false);
            reset.animate().rotation(360).setDuration(1000);
            statuses.setSelection(0);
            categories.setSelection(0);
            regions.setSelection(0);
            districts.setSelection(0);
            realEstateAge.setText("");
            seekBarPrice.setSelectedMinValue((int) seekBarPrice.getMinValue(), true);
            seekBarPrice.setSelectedMaxValue((int) seekBarPrice.getMaxValue(), true);
            minPrice.setText(getString(R.string.price_amount,String.valueOf((int) seekBarPrice.getMinValue())));
            maxPrice.setText(getString(R.string.price_amount,String.valueOf((int) seekBarPrice.getMaxValue())));
            seekBarArea.setSelectedMinValue((int) seekBarArea.getMinValue(), true);
            seekBarArea.setSelectedMaxValue((int) seekBarArea.getMaxValue(), true);
            minArea.setText(getString(R.string.value_area,String.valueOf((int) seekBarArea.getMinValue())));
            maxArea.setText(getString(R.string.value_area,String.valueOf((int) seekBarArea.getMaxValue())));

            new Handler().postDelayed(() -> {
                reset.animate().rotation(0).setDuration(0);
                reset.setEnabled(true);
            }, 896);
        });
    }

    private void setRealEstateStatuses(AppCompatSpinner spinner) {
        List<String> listStatuses = new ArrayList<>();
        RealEstateStatusesViewModel viewModelStatuses = ViewModelProviders.of(getActivity()).get(RealEstateStatusesViewModel.class);
        viewModelStatuses.getStatuses().observe(this, realEstateStatusesModelResponse -> {
            if (realEstateStatusesModelResponse.getKey().equals(SUCCESS)) {
                listStatuses.clear();
                listStatuses.add(getString(R.string.choose_status));
                for (Status status : realEstateStatusesModelResponse.getStatuses()) {
                    listStatuses.add(status.getName());
                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listStatuses);
                spinner.setAdapter(adapter);
            }
        });
    }

    private void setRealEstateCategories(AppCompatSpinner spinner) {
        List<String> listCategories = new ArrayList<>();
        RealEstateCategoriesViewModel viewModelCategories = ViewModelProviders.of(getActivity()).get(RealEstateCategoriesViewModel.class);
        viewModelCategories.getCategories().observe(this, realEstateCategoriesModelResponse -> {
            if (realEstateCategoriesModelResponse.getKey().equals(SUCCESS)) {
                listCategories.clear();
                listCategories.add(getString(R.string.choose_category));
                for (Category category : realEstateCategoriesModelResponse.getCategories()) {
                    listCategories.add(category.getName());
                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listCategories);
                spinner.setAdapter(adapter);
            }
        });
    }

    private void setRegions(AppCompatSpinner spinner) {
        List<String> listRegions = new ArrayList<>();
        RegionsViewModel viewModelRegions = ViewModelProviders.of(getActivity()).get(RegionsViewModel.class);
        viewModelRegions.getRegions().observe(this, regionsModelResponse -> {
            if (regionsModelResponse.getKey().equals(SUCCESS)) {
                listRegions.clear();
                listRegions.add(getString(R.string.choose_region));
                listRegions.addAll(Arrays.asList(regionsModelResponse.getRegions()));
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listRegions);
                spinner.setAdapter(adapter);
            }
        });
    }

    private void setDistricts(AppCompatSpinner spinner) {
        List<String> listDistricts = new ArrayList<>();
        DistrictsViewModel viewModelDistricts = ViewModelProviders.of(getActivity()).get(DistrictsViewModel.class);
        viewModelDistricts.getDistricts().observe(this, districtsModelResponse -> {
            if (districtsModelResponse.getKey().equals(SUCCESS)) {
                listDistricts.clear();
                listDistricts.add(getString(R.string.choose_district));
                listDistricts.addAll(Arrays.asList(districtsModelResponse.getDistricts()));
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), R.layout.list_item_spinner, listDistricts);
                spinner.setAdapter(adapter);
            }
        });
    }
}
