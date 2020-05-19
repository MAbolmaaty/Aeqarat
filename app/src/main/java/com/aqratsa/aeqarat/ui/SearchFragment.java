package com.aqratsa.aeqarat.ui;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.SearchAdapter;
import com.aqratsa.aeqarat.models.real_estate_categories.Category;
import com.aqratsa.aeqarat.models.real_estate_statuses.Status;
import com.aqratsa.aeqarat.models.search.request.SearchModelRequest;
import com.aqratsa.aeqarat.models.search.response.RealEstate;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.SoftKeyboard;
import com.aqratsa.aeqarat.viewmodels.DistrictsViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateCategoriesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateStatusesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstatesViewModel;
import com.aqratsa.aeqarat.viewmodels.RegionsViewModel;
import com.aqratsa.aeqarat.viewmodels.SearchViewModel;
import com.github.guilhe.views.SeekBarRangedView;
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
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();

    @BindView(R.id.results)
    RecyclerView mResults;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.menu)
    ImageView mMenu;
    @BindView(R.id.filter)
    ImageView mFilter;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.noRealEstates)
    TextView mNoRealEstates;

    private SearchAdapter mSearchAdapter;
    private SearchViewModel mViewModelSearch;
    private List<RealEstate> mListResults = new ArrayList<>();
    private RealEstateViewModel mViewModelRealEstate;
    private BottomSheetDialog mDialogFilter;
    private RealEstatesViewModel mViewModelRealEstates;
    private Toast mToast;
    private String mLocale;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        mViewModelSearch = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        mViewModelRealEstate = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        mViewModelRealEstates = ViewModelProviders.of(getActivity()).get(RealEstatesViewModel.class);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mResults.setLayoutManager(layoutManager);
        mResults.setHasFixedSize(true);

        //SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setFocusable(false);
        mSearchView.findViewById(androidx.appcompat.R.id.search_plate).setBackgroundColor(Color.TRANSPARENT);
        ((LinearLayout) mSearchView.findViewById(R.id.search_voice_btn).getParent()).setBackgroundColor(Color.TRANSPARENT);

        mViewModelSearch.getParameters().observe(this, searchModelRequest -> {
            mViewModelSearch.search(searchModelRequest.getCategory(), searchModelRequest.getStatus(),
                    searchModelRequest.getMinPrice(), searchModelRequest.getMaxPrice(),
                    searchModelRequest.getAddress(), searchModelRequest.getRegion(),
                    searchModelRequest.getDistrict(), searchModelRequest.getRealEstateAge(),
                    searchModelRequest.getMinArea(), searchModelRequest.getMaxArea(),
                    searchModelRequest.getRegionItemPosition(),
                    searchModelRequest.getDistrictItemPosition());

            mViewModelSearch.getResults().observe(SearchFragment.this, searchModelResponse -> {
                if (searchModelResponse.getKey().equals(SUCCESS)) {
                    mListResults.clear();
                    mListResults.addAll(Arrays.asList(searchModelResponse.getRealEstates()));
                    mSearchAdapter = new SearchAdapter(SearchFragment.this.getContext(), mListResults, position -> {
                        mViewModelRealEstate.setRealEstateId(mListResults.get(position).getId());
                        loadFragment(SearchFragment.this.getActivity().getSupportFragmentManager(),
                                new RealEstateFragment(), true);

                    });
                    mResults.setAdapter(mSearchAdapter);
                    if (mListResults.size() < 1) {
                        mNoRealEstates.setVisibility(View.VISIBLE);
                    } else {
                        mNoRealEstates.setVisibility(View.GONE);
                    }
                } else {
                    mToast = Toast.makeText(SearchFragment.this.getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });

            mViewModelSearch.isLoading().observe(SearchFragment.this, loading -> {
                if (loading) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            });

            mViewModelSearch.failure().observe(SearchFragment.this, failure -> {
                if (failure){
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
        });

        mDialogFilter = new BottomSheetDialog(getContext());
        mDialogFilter.setContentView(R.layout.dialog_filter);
        setDialogFilter();

        mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, "en");
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

    @OnClick(R.id.menu)
    public void menu() {
        sDrawerLayout.openDrawer(GravityCompat.START);
        SoftKeyboard.dismissKeyboardInActivity(getActivity());
    }

    @OnClick(R.id.filter)
    public void openFilter() {
        mDialogFilter.show();
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

        mDialogFilter.setOnDismissListener(dialog12 -> scrollView.scrollTo(0,0));
        title.setText(R.string.search_advanced);
        close.setImageResource(R.drawable.ic_close);
        close.setOnClickListener(v -> mDialogFilter.cancel());

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
                for (com.aqratsa.aeqarat.models.real_estates.response.RealEstate realEstate : realEstatesModelResponse.getRealEstates()) {
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

                for (com.aqratsa.aeqarat.models.real_estates.response.RealEstate realEstate : realEstatesModelResponse.getRealEstates()) {
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
                for (com.aqratsa.aeqarat.models.real_estates.response.RealEstate realEstate : realEstatesModelResponse.getRealEstates()) {
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

                loadFragment(SearchFragment.this.getActivity().getSupportFragmentManager(),
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
            minPrice.setText(getString(R.string.price_amount, String.valueOf((int) seekBarPrice.getMinValue())));
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
