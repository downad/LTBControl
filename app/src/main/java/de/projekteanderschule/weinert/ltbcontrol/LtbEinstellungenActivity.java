package de.projekteanderschule.weinert.ltbcontrol;

import android.preference.PreferenceActivity;
import android.util.Log;

import java.util.List;

/**
 * Created by ralf on 29.05.17.
 */

public class LtbEinstellungenActivity  extends PreferenceActivity {


    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.headers_ltbeinstellungen, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        //return true;

        // prüfe ob das Fragment existiert
        if (fragmentName.equals("de.projekteanderschule.weinert.ltbcontrol.Preferences_Fragment_Mail")) {
            Log.d("Ralf" , " FragmentName aus Class = " + Preferences_Fragment_Mail.class.getName());
            return Preferences_Fragment_Mail.class.getName().equals(fragmentName);
        } //else {
        if (fragmentName.equals("de.projekteanderschule.weinert.ltbcontrol.Preferences_Fragment_AutoCheck")) {
            Log.d("Ralf", " FragmentName aus Class = " + Preferences_Fragment_AutoCheck.class.getName());
            return Preferences_Fragment_AutoCheck.class.getName().equals(fragmentName);
        }
        if (fragmentName.equals("de.projekteanderschule.weinert.ltbcontrol.Preferences_Fragment_Test3")) {
            Log.d("Ralf", " FragmentName aus Class = " + Preferences_Fragment_AutoCheck.class.getName());
            return Preferences_Fragment_Test3.class.getName().equals(fragmentName);
        }
        if (fragmentName.equals("de.projekteanderschule.weinert.ltbcontrol.Preferences_Fragment_Test4")) {
            Log.d("Ralf", " FragmentName aus Class = " + Preferences_Fragment_AutoCheck.class.getName());
            return Preferences_Fragment_Test4.class.getName().equals(fragmentName);
        }
        if (fragmentName.equals("de.projekteanderschule.weinert.ltbcontrol.Preferences_Fragment_Test5")) {
            Log.d("Ralf", " FragmentName aus Class = " + Preferences_Fragment_AutoCheck.class.getName());
            return Preferences_Fragment_Test5.class.getName().equals(fragmentName);
        }
            //}
        return false;

    }
}