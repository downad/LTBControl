package de.projekteanderschule.weinert.ltbcontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by ralf on 29.05.17.
 */

public class Preferences_Fragment_Mail extends PreferenceFragment {

    EditTextPreference HostEditTextPreference;
    EditTextPreference UsernameEditTextPreference;
    EditTextPreference PasswordEditTextPreference;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_fragment_mail);

        HostEditTextPreference = (EditTextPreference)findPreference("host");
        UsernameEditTextPreference = (EditTextPreference)findPreference("username");
        PasswordEditTextPreference = (EditTextPreference)findPreference("password");

        SharedPreferences getUserData = PreferenceManager.getDefaultSharedPreferences(getActivity()); //"preference_fragment_mail.xml", Context.MODE_PRIVATE);

        HostEditTextPreference.setText(getUserData.getString("host", ConfigData.getHost()));
        UsernameEditTextPreference.setText(getUserData.getString("username", ConfigData.getUsername()));
        PasswordEditTextPreference.setText(getUserData.getString("password", ConfigData.getPassword()));
    }
}
