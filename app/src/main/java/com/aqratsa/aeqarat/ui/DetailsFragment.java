package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.AUCTIONABLE;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.PRIVATE;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;
import static com.aqratsa.aeqarat.utils.Constants.SHARED;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsFragment.class.getSimpleName();

    //Ad Number
    @BindView(R.id.view_adNumber)
    View mViewAdNumber;
    @BindView(R.id.imageView_adNumber)
    ImageView mImageViewAdNumber;
    @BindView(R.id.textView_adNumber)
    TextView mTextViewAdNumber;
    @BindView(R.id.adNumber)
    TextView mAdNumber;
    //Age
    @BindView(R.id.view_age)
    View mViewAge;
    @BindView(R.id.imageView_age)
    ImageView mImageViewAge;
    @BindView(R.id.textView_age)
    TextView mTextViewAge;
    @BindView(R.id.age)
    TextView mAge;
    //Area
    @BindView(R.id.view_area)
    View mViewArea;
    @BindView(R.id.imageView_area)
    ImageView mImageViewArea;
    @BindView(R.id.textView_area)
    TextView mTextViewArea;
    @BindView(R.id.areaInMeter)
    TextView mTextViewAreaInMeter;
    @BindView(R.id.area)
    TextView mArea;
    //Rooms
    @BindView(R.id.view_rooms)
    View mViewRooms;
    @BindView(R.id.imageView_rooms)
    ImageView mImageViewRooms;
    @BindView(R.id.textView_rooms)
    TextView mTextViewRooms;
    @BindView(R.id.rooms)
    TextView mRooms;
    //Insurance
    @BindView(R.id.view_insurance)
    View mViewInsurance;
    @BindView(R.id.imageView_insurance)
    ImageView mImageViewInsurance;
    @BindView(R.id.textView_insurance)
    TextView mTextViewInsurance;
    @BindView(R.id.insuranceCurrency)
    TextView mInsuranceCurrency;
    @BindView(R.id.insurance)
    TextView mInsurance;
    //Apartments
    @BindView(R.id.view_apartments)
    View mViewApartments;
    @BindView(R.id.imageView_apartments)
    ImageView mImageViewApartments;
    @BindView(R.id.textView_apartments)
    TextView mTextViewApartments;
    @BindView(R.id.apartments)
    TextView mApartments;
    //Electricity
    @BindView(R.id.view_electricity)
    View mViewElectricity;
    @BindView(R.id.imageView_electricity)
    ImageView mImageViewElectricity;
    @BindView(R.id.textView_electricity)
    TextView mTextViewElectricity;
    @BindView(R.id.electricityStatus)
    TextView mElectricityStatus;
    @BindView(R.id.electricity)
    TextView mElectricity;
    //Water
    @BindView(R.id.view_water)
    View mViewWater;
    @BindView(R.id.imageView_water)
    ImageView mImageViewWater;
    @BindView(R.id.textView_water)
    TextView mTextViewWater;
    @BindView(R.id.waterStatus)
    TextView mWaterStatus;
    @BindView(R.id.water)
    TextView mWater;
    //Bathrooms
    @BindView(R.id.view_bathrooms)
    View mViewBathrooms;
    @BindView(R.id.imageView_bathrooms)
    ImageView mImageViewBathrooms;
    @BindView(R.id.textView_bathrooms)
    TextView mTextViewBathrooms;
    @BindView(R.id.bathrooms)
    TextView mBathrooms;
    //Floors
    @BindView(R.id.view_floors)
    View mViewFloors;
    @BindView(R.id.imageView_floors)
    ImageView mImageViewFloors;
    @BindView(R.id.textView_floors)
    TextView mTextViewFloors;
    @BindView(R.id.floors)
    TextView mFloors;
    //Offices
    @BindView(R.id.view_offices)
    View mViewOffices;
    @BindView(R.id.imageView_offices)
    ImageView mImageViewOffices;
    @BindView(R.id.textView_offices)
    TextView mTextViewOffices;
    @BindView(R.id.offices)
    TextView mOffices;
    //Shops
    @BindView(R.id.view_shops)
    View mViewShops;
    @BindView(R.id.imageView_shops)
    ImageView mImageViewShops;
    @BindView(R.id.textView_shops)
    TextView mTextViewShops;
    @BindView(R.id.shops)
    TextView mShops;
    //Halls
    @BindView(R.id.view_halls)
    View mViewHalls;
    @BindView(R.id.imageView_halls)
    ImageView mImageViewHalls;
    @BindView(R.id.textView_halls)
    TextView mTextViewHalls;
    @BindView(R.id.halls)
    TextView mHalls;
    //Street Width
    @BindView(R.id.view_streetWidth)
    View mViewStreetWidth;
    @BindView(R.id.imageView_streetWidth)
    ImageView mImageViewStreetWidth;
    @BindView(R.id.textView_streetWidth)
    TextView mTextViewStreetWidth;
    @BindView(R.id.streetWidthInMeter)
    TextView mTextViewStreetWidthInMeter;
    @BindView(R.id.streetWidth)
    TextView mStreetWidth;

    private RealEstateViewModel mViewModelRealEstate;
    private String mLocale;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (mLocale.equals("ar"))
            view.setRotation(-180);
        ButterKnife.bind(this, view);

        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelRealEstate.getRealEstate().observe(this, realEstateModelResponse -> {
            if (realEstateModelResponse.getRealEstate() != null){
                if (realEstateModelResponse.getRealEstate().getId() != null){
                    showAdNumber();
                    mAdNumber.setText(realEstateModelResponse.getRealEstate().getId());
                }
                if (realEstateModelResponse.getRealEstate().getYear_of_building() != null){
                    showAge();
                    mAge.setText(realEstateModelResponse.getRealEstate().getYear_of_building());
                }
                if (realEstateModelResponse.getRealEstate().getApartment_space() != null){
                    showArea();
                    mArea.setText(realEstateModelResponse.getRealEstate().getApartment_space());
                }
                if (realEstateModelResponse.getRealEstate().getRooms_num() != null){
                    showRooms();
                    mRooms.setText(realEstateModelResponse.getRealEstate().getRooms_num());
                }
                if (realEstateModelResponse.getRealEstate().getAmount_insurance() != null){
                    if (realEstateModelResponse.getRealEstate().getService().equals(REAL_ESTATE_SALE)
                    && !realEstateModelResponse.getRealEstate().getAuction().equals(AUCTIONABLE)){
                    } else {
                        showInsurance();
                        mInsurance.setText(realEstateModelResponse.getRealEstate().getAmount_insurance());
                    }
                }
                if (realEstateModelResponse.getRealEstate().getApartments_num() != null){
                    showApartments();
                    mApartments.setText(realEstateModelResponse.getRealEstate().getApartments_num());
                }
                if (realEstateModelResponse.getRealEstate().getElectrical_meter_num() != null){
                    showElectricity();
                    mElectricity.setText(realEstateModelResponse.getRealEstate().getElectrical_meter_num());
                    if (realEstateModelResponse.getRealEstate().getElectricityStatus().equals(SHARED)){
                        mElectricityStatus.setVisibility(View.VISIBLE);
                        mElectricityStatus.setText(R.string.shared_acct);
                    } else if (realEstateModelResponse.getRealEstate().getElectricityStatus().equals(PRIVATE)){
                        mElectricityStatus.setVisibility(View.VISIBLE);
                        mElectricityStatus.setText(R.string.private_acct);
                    }
                }
                if (realEstateModelResponse.getRealEstate().getWater_meter_num() != null){
                    showWater();
                    mWater.setText(realEstateModelResponse.getRealEstate().getWater_meter_num());
                    if (realEstateModelResponse.getRealEstate().getWaterStatus().equals(SHARED)){
                        mWaterStatus.setVisibility(View.VISIBLE);
                        mWaterStatus.setText(R.string.shared_acct);
                    } else if (realEstateModelResponse.getRealEstate().getWaterStatus().equals(PRIVATE)){
                        mWaterStatus.setVisibility(View.VISIBLE);
                        mWaterStatus.setText(R.string.private_acct);
                    }
                }
                if (realEstateModelResponse.getRealEstate().getBath_num() != null){
                    showBathrooms();
                    mBathrooms.setText(realEstateModelResponse.getRealEstate().getBath_num());
                }
                if (realEstateModelResponse.getRealEstate().getFloor_num() != null){
                    showFloors();
                    mFloors.setText(realEstateModelResponse.getRealEstate().getFloor_num());
                }
                if (realEstateModelResponse.getRealEstate().getOffices() != null){
                    showOffices();
                    mOffices.setText(realEstateModelResponse.getRealEstate().getOffices());
                }
                if (realEstateModelResponse.getRealEstate().getShops() != null){
                    showShops();
                    mShops.setText(realEstateModelResponse.getRealEstate().getShops());
                }
                if (realEstateModelResponse.getRealEstate().getHalls() != null){
                    showHalls();
                    mHalls.setText(realEstateModelResponse.getRealEstate().getHalls());
                }
                if (realEstateModelResponse.getRealEstate().getStreetWidth() != null){
                    showStreetWidth();
                    mStreetWidth.setText(realEstateModelResponse.getRealEstate().getStreetWidth());
                }
            }
        });

        return view;
    }

    private void showAdNumber(){
        mViewAdNumber.setVisibility(View.VISIBLE);
        mImageViewAdNumber.setVisibility(View.VISIBLE);
        mTextViewAdNumber.setVisibility(View.VISIBLE);
        mAdNumber.setVisibility(View.VISIBLE);
    }

    private void showAge(){
        mViewAge.setVisibility(View.VISIBLE);
        mImageViewAge.setVisibility(View.VISIBLE);
        mTextViewAge.setVisibility(View.VISIBLE);
        mAge.setVisibility(View.VISIBLE);
    }

    private void showArea(){
        mViewArea.setVisibility(View.VISIBLE);
        mImageViewArea.setVisibility(View.VISIBLE);
        mTextViewArea.setVisibility(View.VISIBLE);
        mTextViewAreaInMeter.setVisibility(View.VISIBLE);
        mArea.setVisibility(View.VISIBLE);
    }

    private void showRooms(){
        mViewRooms.setVisibility(View.VISIBLE);
        mImageViewRooms.setVisibility(View.VISIBLE);
        mTextViewRooms.setVisibility(View.VISIBLE);
        mRooms.setVisibility(View.VISIBLE);
    }

    private void showInsurance(){
        mViewInsurance.setVisibility(View.VISIBLE);
        mImageViewInsurance.setVisibility(View.VISIBLE);
        mTextViewInsurance.setVisibility(View.VISIBLE);
        mInsuranceCurrency.setVisibility(View.VISIBLE);
        mInsurance.setVisibility(View.VISIBLE);
    }

    private void showApartments(){
        mViewApartments.setVisibility(View.VISIBLE);
        mImageViewApartments.setVisibility(View.VISIBLE);
        mTextViewApartments.setVisibility(View.VISIBLE);
        mApartments.setVisibility(View.VISIBLE);
    }

    private void showElectricity(){
        mViewElectricity.setVisibility(View.VISIBLE);
        mImageViewElectricity.setVisibility(View.VISIBLE);
        mTextViewElectricity.setVisibility(View.VISIBLE);
        mElectricity.setVisibility(View.VISIBLE);
    }

    private void showWater(){
        mViewWater.setVisibility(View.VISIBLE);
        mImageViewWater.setVisibility(View.VISIBLE);
        mTextViewWater.setVisibility(View.VISIBLE);
        mWater.setVisibility(View.VISIBLE);
    }

    private void showBathrooms(){
        mViewBathrooms.setVisibility(View.VISIBLE);
        mImageViewBathrooms.setVisibility(View.VISIBLE);
        mTextViewBathrooms.setVisibility(View.VISIBLE);
        mBathrooms.setVisibility(View.VISIBLE);
    }

    private void showFloors(){
        mViewFloors.setVisibility(View.VISIBLE);
        mImageViewFloors.setVisibility(View.VISIBLE);
        mTextViewFloors.setVisibility(View.VISIBLE);
        mFloors.setVisibility(View.VISIBLE);
    }

    private void showOffices(){
        mViewOffices.setVisibility(View.VISIBLE);
        mImageViewOffices.setVisibility(View.VISIBLE);
        mTextViewOffices.setVisibility(View.VISIBLE);
        mOffices.setVisibility(View.VISIBLE);
    }

    private void showShops(){
        mViewShops.setVisibility(View.VISIBLE);
        mImageViewShops.setVisibility(View.VISIBLE);
        mTextViewShops.setVisibility(View.VISIBLE);
        mShops.setVisibility(View.VISIBLE);
    }

    private void showHalls(){
        mViewHalls.setVisibility(View.VISIBLE);
        mImageViewHalls.setVisibility(View.VISIBLE);
        mTextViewHalls.setVisibility(View.VISIBLE);
        mHalls.setVisibility(View.VISIBLE);
    }

    private void showStreetWidth(){
        mViewStreetWidth.setVisibility(View.VISIBLE);
        mImageViewStreetWidth.setVisibility(View.VISIBLE);
        mTextViewStreetWidth.setVisibility(View.VISIBLE);
        mTextViewStreetWidthInMeter.setVisibility(View.VISIBLE);
        mStreetWidth.setVisibility(View.VISIBLE);
    }

    public static DetailsFragment newInstance(){
        return new DetailsFragment();
    }
}
