package com.example.soham.intro_design;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kelvin on 2/24/2017.
 */
public class IntroManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "snow-intro-slider";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public IntroManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();

    }

    public boolean Check()
    {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }


    public void setFirst(Boolean isFirst)
    {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirst);
        editor.commit();
    }


}
