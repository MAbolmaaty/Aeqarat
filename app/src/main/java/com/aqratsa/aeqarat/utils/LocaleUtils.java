package com.aqratsa.aeqarat.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleUtils {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context){
        String locale = getLocale(context, Locale.getDefault().getLanguage());
        return setLocale(context, locale);
    }

    public static Context onAttach(Context context, String defaultLocale){
        String locale = getLocale(context, defaultLocale);
        return setLocale(context, locale);
    }

    public static Context setLocale(Context context, String locale){
        changeLocale(context, locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return updateResources(context, locale);

        return updateResourcesLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String locale) {
        Locale currentLocale = new Locale(locale);
        Locale.setDefault(currentLocale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(currentLocale);
        configuration.setLayoutDirection(currentLocale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String locale) {
        Locale currentLocale = new Locale(locale);
        Locale.setDefault(currentLocale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        /*configuration.locale = currentLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLayoutDirection(currentLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());*/
        configuration.setLocale(currentLocale);
        return context;
    }

    private static void changeLocale(Context context, String locale){
        SharedPrefUtil.getInstance(context).write(SELECTED_LANGUAGE, locale);
    }

    private static String getLocale(Context context, String locale){
        return SharedPrefUtil.getInstance(context).read(SELECTED_LANGUAGE, locale);
    }
}
