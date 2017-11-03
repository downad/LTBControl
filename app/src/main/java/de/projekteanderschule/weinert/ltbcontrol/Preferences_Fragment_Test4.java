package de.projekteanderschule.weinert.ltbcontrol;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by ralf on 29.05.17.
 */

public class Preferences_Fragment_Test4 extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_fragment_autocheck);
    }
}
