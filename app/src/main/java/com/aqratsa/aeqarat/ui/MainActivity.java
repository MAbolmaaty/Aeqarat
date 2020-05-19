package com.aqratsa.aeqarat.ui;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.info_user.UserInfoModelResponse;
import com.aqratsa.aeqarat.models.search.request.SearchModelRequest;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.viewmodels.DistrictsViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUpdateViewModel;
import com.aqratsa.aeqarat.viewmodels.InfoUserViewModel;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateCategoriesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstateStatusesViewModel;
import com.aqratsa.aeqarat.viewmodels.RealEstatesViewModel;
import com.aqratsa.aeqarat.viewmodels.RegionsViewModel;
import com.aqratsa.aeqarat.viewmodels.SearchViewModel;
import com.aqratsa.aeqarat.viewmodels.SlidesViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.aqratsa.aeqarat.utils.Constants.ADDRESS;
import static com.aqratsa.aeqarat.utils.Constants.AGENT;
import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;
import static com.aqratsa.aeqarat.utils.Constants.BIRTH_DATE;
import static com.aqratsa.aeqarat.utils.Constants.EMAIL;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.PHONE_CODE;
import static com.aqratsa.aeqarat.utils.Constants.PHONE_NUMBER;
import static com.aqratsa.aeqarat.utils.Constants.REGISTER;
import static com.aqratsa.aeqarat.utils.Constants.REMEMBER_ME;
import static com.aqratsa.aeqarat.utils.Constants.USERNAME;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;
import static com.aqratsa.aeqarat.utils.Constants.USER_IMAGE;
import static com.aqratsa.aeqarat.utils.Constants.USER_TYPE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nav_view)
    NavigationView mNavView;

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static DrawerLayout sDrawerLayout;

    private SearchViewModel mViewModelSearch;
    private LoginViewModel mViewModelLogin;
    private Toast mToast;
    private boolean mLoggedIn;

    @Override
    protected void attachBaseContext(Context newBase) {
        String language = SharedPrefUtil.getInstance(newBase).read(LOCALE, Locale.getDefault().getLanguage());
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = newBase.getResources().getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        newBase = newBase.createConfigurationContext(config);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set App Language
        String language = SharedPrefUtil.getInstance(this).read(LOCALE, Locale.getDefault().getLanguage());
        Locale locale = new Locale(language);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        createConfigurationContext(configuration);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        String locale1 = SharedPrefUtil.getInstance(this).read(LOCALE, Locale.getDefault().getLanguage());

        LoginViewModel viewModelLogin = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModelLogin.isLoggedIn().observe(this, loggedIn -> {
            mLoggedIn = loggedIn;
            mNavView.getMenu().getItem(11).setVisible(loggedIn);
            mNavView.getMenu().getItem(0).setChecked(true);
            mNavView.getMenu().getItem(1).setChecked(true);
            mNavView.getMenu().getItem(3).setVisible(true);
            mNavView.getMenu().getItem(4).setVisible(false);
            mNavView.getMenu().getItem(1).setVisible(false);
            mNavView.getMenu().getItem(0).setVisible(true);
            mNavView.getMenu().getItem(2).setVisible(true);
            mNavView.getMenu().getItem(5).setVisible(true);
            mNavView.getMenu().getItem(10).setVisible(true);
            ((NavigationMenuView)mNavView.getChildAt(0)).scrollToPosition(0);
            setNavHeader(loggedIn);
            if (loggedIn){
                String userId = SharedPrefUtil.getInstance(this).read(USER_ID, null);
                String userType;
                if (userId != null){
                    userType = SharedPrefUtil.getInstance(this).read(USER_TYPE, "");
                    if (userType.equals(AGENT)){
                        mNavView.getMenu().getItem(4).setVisible(true);
                        mNavView.getMenu().getItem(1).setVisible(true);
                        mNavView.getMenu().getItem(2).setVisible(false);
                        mNavView.getMenu().getItem(0).setVisible(false);
                        mNavView.getMenu().getItem(3).setVisible(false);
                        mNavView.getMenu().getItem(5).setVisible(false);
                        mNavView.getMenu().getItem(10).setVisible(false);
                        loadFragment(getSupportFragmentManager(), new RealEstatesAgentFragment(),
                                false);
                    } else {
                        loadFragment(getSupportFragmentManager(),
                                new HomeFragment(), false);
                    }
                } else {
                    mViewModelLogin.getUser().observe(this, loginModelResponse -> {
                        if (loginModelResponse.getUser().getType().equals(AGENT)){
                            mNavView.getMenu().getItem(4).setVisible(true);
                            mNavView.getMenu().getItem(3).setVisible(false);
                            mNavView.getMenu().getItem(1).setVisible(true);
                            mNavView.getMenu().getItem(2).setVisible(false);
                            mNavView.getMenu().getItem(0).setVisible(false);
                            mNavView.getMenu().getItem(5).setVisible(false);
                            mNavView.getMenu().getItem(10).setVisible(false);
                            loadFragment(getSupportFragmentManager(), new RealEstatesAgentFragment(),
                                    false);
                        }else {
                            loadFragment(getSupportFragmentManager(),
                                    new HomeFragment(), false);
                        }
                    });
                }
            }
        });

        //Update navigation header due to settings updated
        navHeaderUpdated();

        sDrawerLayout = findViewById(R.id.drawer_layout);

        mNavView.getChildAt(0).setVerticalScrollBarEnabled(false);
        mNavView.getMenu().getItem(11).setVisible(false);

        mViewModelLogin = ViewModelProviders.of(this).get(LoginViewModel.class);
        mViewModelSearch = ViewModelProviders.of(this).get(SearchViewModel.class);

        //Get RealEstates
        RealEstatesViewModel viewModelRealEstates = ViewModelProviders.of(this).get(RealEstatesViewModel.class);
        viewModelRealEstates.realEstates("1", "1", "0");
        //Get Slides
        SlidesViewModel viewModelSlides = ViewModelProviders.of(this).get(SlidesViewModel.class);
        viewModelSlides.slides();
        //Get Real Estate Statuses, Ex : sale, rent
        RealEstateStatusesViewModel viewModelRealEstateStatuses = ViewModelProviders.of(this).get(RealEstateStatusesViewModel.class);
        viewModelRealEstateStatuses.statuses(locale1);
        //Get Real Estate Categories, Ex : apartment, office
        RealEstateCategoriesViewModel viewModelRealEstateCategories = ViewModelProviders.of(this).get(RealEstateCategoriesViewModel.class);
        viewModelRealEstateCategories.categories(locale1);
        //Get Regions
        RegionsViewModel viewModelRegions = ViewModelProviders.of(this).get(RegionsViewModel.class);
        viewModelRegions.regions(locale1);
        //Get Districts
        DistrictsViewModel viewModelDistricts = ViewModelProviders.of(this).get(DistrictsViewModel.class);
        viewModelDistricts.districts(locale1);

        //Splash
        viewSplash();

        // Checking for Google play services availability
        if (isGooglePlayServicesAvailable()) {

        }

        setNavigationItemSelected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleSearchIntent(intent);
    }

    private void setNavigationItemSelected() {
        mNavView.setNavigationItemSelectedListener(menuItem -> {
            if (mToast != null)
                mToast.cancel();
            mToast = Toast.makeText(this, R.string.login_first, Toast.LENGTH_SHORT);
            sDrawerLayout.closeDrawer(GravityCompat.START, false);

            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.nav_home:
                    loadFragment(getSupportFragmentManager(), new HomeFragment(), false);
                    return true;
                case R.id.nav_adminRealEstates:
                    loadFragment(getSupportFragmentManager(), new RealEstatesAgentFragment(), false);
                    return true;
                case R.id.nav_realEstates:
                    if (mLoggedIn) {
                        loadFragment(getSupportFragmentManager(), new RealEstatesFragment(), false);
                        return true;
                    } else {
                        mToast.show();
                        return false;
                    }
                case R.id.nav_requests:
                    if (mLoggedIn) {
                        loadFragment(getSupportFragmentManager(), new RequestsFragment(), false);
                        return true;
                    } else {
                        mToast.show();
                        return false;
                    }
                case R.id.nav_requests_admin:
                    loadFragment(getSupportFragmentManager(), new RequestsAgentFragment(), false);
                    return true;
                case R.id.nav_messages:
                    if (mLoggedIn) {
                        loadFragment(getSupportFragmentManager(), new MessagesFragment(), false);
                        return true;
                    } else {
                        mToast.show();
                        return false;
                    }
                case R.id.nav_notifications:
                    if (mLoggedIn) {
                        loadFragment(getSupportFragmentManager(), new NotificationsFragment(), false);
                        return true;
                    } else {
                        mToast.show();
                        return false;
                    }
                case R.id.nav_favorites:
                    if (mLoggedIn) {
                        loadFragment(getSupportFragmentManager(), new FavoritesFragment(), false);
                        return true;
                    } else {
                        mToast.show();
                        return false;
                    }
                case R.id.nav_privacyPolicy:
                    loadFragment(getSupportFragmentManager(), new PrivacyPolicyFragment(), false);
                    return true;
                case R.id.nav_contactUs:
                    loadFragment(getSupportFragmentManager(), new ContactUsFragment(), false);
                    return true;
                case R.id.nav_settings:
                    if (mLoggedIn) {
                        loadFragment(getSupportFragmentManager(), new SettingsFragment(), false);
                        return true;
                    } else {
                        mToast.show();
                        return false;
                    }
                case R.id.nav_logout:
                    logout();
                    loadFragment(getSupportFragmentManager(), new LoginFragment(), false);
                    return true;
            }
            return false;
        });
    }

    private void handleSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String address = intent.getStringExtra(SearchManager.QUERY);
            mViewModelSearch.setParameters(new SearchModelRequest(null, null, null, null,
                    address, null, null, null, null, null,
                    0, 0));
            loadFragment(getSupportFragmentManager(), new SearchFragment(), false);
        }
    }

    //Splash
    private void viewSplash() {
        loadFragment(getSupportFragmentManager(), new SplashFragment(), false);
    }

    public boolean isGooglePlayServicesAvailable() {

        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, getString(R.string.google_play_services_not_available), Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void logout() {
        mViewModelLogin.loggedIn(false);
        SharedPrefUtil.getInstance(this).write(REMEMBER_ME, false);
        SharedPrefUtil.getInstance(this).write(USER_ID, null);
        SharedPrefUtil.getInstance(this).write(USER_IMAGE, null);
        SharedPrefUtil.getInstance(this).write(USERNAME, null);
        SharedPrefUtil.getInstance(this).write(USER_TYPE, null);
        SharedPrefUtil.getInstance(this).write(BIRTH_DATE, null);
        SharedPrefUtil.getInstance(this).write(ADDRESS, null);
        SharedPrefUtil.getInstance(this).write(EMAIL, null);
        SharedPrefUtil.getInstance(this).write(PHONE_NUMBER, null);
        SharedPrefUtil.getInstance(this).write(PHONE_CODE, null);
    }

    private void setNavHeader(boolean loggedIn) {
        View header = mNavView.getHeaderView(0);
        ImageView closeMenu = header.findViewById(R.id.close);
        Button login = header.findViewById(R.id.login);
        Button register = header.findViewById(R.id.register);
        CircleImageView userImage = header.findViewById(R.id.userImage);
        TextView username = header.findViewById(R.id.userName);
        TextView userType = header.findViewById(R.id.userType);
        closeMenu.setOnClickListener(v -> sDrawerLayout.closeDrawer(GravityCompat.START, false));
        if (loggedIn) {
            login.setVisibility(View.INVISIBLE);
            register.setVisibility(View.INVISIBLE);
            userImage.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            userType.setVisibility(View.VISIBLE);
            String userId = SharedPrefUtil.getInstance(this).read(USER_ID, null);
            if (userId == null) {
                mViewModelLogin.getUser().observe(this, loginModelResponse -> {
                    String image = loginModelResponse.getUser().getPhoto();
                    String name = loginModelResponse.getUser().getName();
                    String type = loginModelResponse.getUser().getType();

                    Glide.with(MainActivity.this)
                            .load(BASE_URL + image)
                            .into(userImage);

                    username.setText(name);

                    setUserType(Integer.parseInt(type), userType);
                });
            } else {
                String image = SharedPrefUtil.getInstance(this).read(USER_IMAGE, null);
                String name = SharedPrefUtil.getInstance(this).read(USERNAME, null);
                String type = SharedPrefUtil.getInstance(this).read(USER_TYPE, null);

                Glide.with(MainActivity.this)
                        .load(BASE_URL + image)
                        .into(userImage);
                username.setText(name);
                if (type != null)
                    setUserType(Integer.parseInt(type), userType);
                else
                    userType.setText("");
            }

            userImage.setOnClickListener(v -> {
                loadFragment(getSupportFragmentManager(), new ProfileFragment(), true);
                sDrawerLayout.closeDrawer(GravityCompat.START, false);
            });

            username.setOnClickListener(v -> {
                loadFragment(getSupportFragmentManager(), new ProfileFragment(), true);
                sDrawerLayout.closeDrawer(GravityCompat.START, false);
            });

            userType.setOnClickListener(v -> {
                loadFragment(getSupportFragmentManager(), new ProfileFragment(), true);
                sDrawerLayout.closeDrawer(GravityCompat.START, false);
            });
        } else {
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
            userImage.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);
            userType.setVisibility(View.INVISIBLE);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(MainActivity.this.getSupportFragmentManager(), new LoginFragment(),
                            false);
                    sDrawerLayout.closeDrawer(GravityCompat.START, false);
                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewModelLogin.confirmEmailFor(REGISTER);
                    loadFragment(MainActivity.this.getSupportFragmentManager(), new EmailConfirmFragment(),
                            true);
                    sDrawerLayout.closeDrawer(GravityCompat.START, false);
                }
            });
        }
    }

    private void navHeaderUpdated() {
        View header = mNavView.getHeaderView(0);
        CircleImageView userImage = header.findViewById(R.id.userImage);
        TextView username = header.findViewById(R.id.userName);
        TextView userType = header.findViewById(R.id.userType);
        InfoUpdateViewModel viewModelInfoUpdate = ViewModelProviders.of(this).get(InfoUpdateViewModel.class);
        viewModelInfoUpdate.isInfoUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean updated) {
                if (updated) {
                    String userId = SharedPrefUtil.getInstance(MainActivity.this).read(USER_ID, null);
                    if (userId == null) {
                        InfoUserViewModel viewModelInfoUser = ViewModelProviders.of(MainActivity.this).get(InfoUserViewModel.class);
                        viewModelInfoUser.getUserInfo().observe(MainActivity.this, new Observer<UserInfoModelResponse>() {
                            @Override
                            public void onChanged(UserInfoModelResponse userInfoModelResponse) {
                                String image = userInfoModelResponse.getUser().getPhoto();
                                String name = userInfoModelResponse.getUser().getName();
                                String type = userInfoModelResponse.getUser().getType();

                                Glide.with(MainActivity.this)
                                        .load(BASE_URL + image)
                                        .into(userImage);
                                username.setText(name);
                                if (type != null)
                                    setUserType(Integer.parseInt(type), userType);
                            }
                        });
                    } else {
                        String image = SharedPrefUtil.getInstance(MainActivity.this).read(USER_IMAGE, null);
                        String name = SharedPrefUtil.getInstance(MainActivity.this).read(USERNAME, null);
                        String type = SharedPrefUtil.getInstance(MainActivity.this).read(USER_TYPE, null);

                        Glide.with(MainActivity.this)
                                .load(BASE_URL + image)
                                .into(userImage);
                        username.setText(name);
                        setUserType(Integer.parseInt(type), userType);
                    }
                }
            }
        });
    }

    private void setUserType(int type, TextView textView) {
        switch (type) {
            case 1:
                textView.setText(R.string.admin);
                break;
            case 2:
                textView.setText(R.string.supervisor);
                break;
            case 3:
                textView.setText(R.string.agent);
                break;
            case 4:
                textView.setText(R.string.real_estate_holder);
                break;
            case 5:
                textView.setText(R.string.individual_owner);
                break;
            case 6:
                textView.setText(R.string.user);
                break;
            default:
                textView.setText("");

        }
    }

    //Attaching Fragment
    public static void loadFragment(FragmentManager fragmentManager,
                                    Fragment fragment,
                                    boolean addToBackStack) {
        if (addToBackStack) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    public static void progress(boolean loading, ProgressBar progressBar, View view) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            view.setEnabled(false);

        } else {
            progressBar.setVisibility(View.GONE);
            view.setEnabled(true);

        }
    }
}
