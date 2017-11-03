package de.projekteanderschule.weinert.ltbcontrol;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ralf on 23.05.17.
 */

public class MainActivityFragment extends Fragment {
    //public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] KlassenListeArray = {"5a", "5b", "6a", "6b", "7a", "7b", "7c", "8a", "8b", "9a"};
        List<String> klassenListe = new ArrayList<>(Arrays.asList(KlassenListeArray));

        ArrayAdapter<String> KlassenListeAdapter =
                new ArrayAdapter<String>(
                        getActivity(),                              //aktuelle Umgebung, diese Activity
                        R.layout.list_item_klassenliste,            //ID der XML-Datei
                        R.id.listview_klassenliste_textview,        //ID der TextView
                        klassenListe                                //ArrayListe
                );
        //return inflater.inflate(R.layout.fragment_main, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        View rootView = inflater.inflate(R.layout.fragment_main, container,false);
        ListView aktienlisteListView = (ListView) rootView.findViewById(R.id.listview_klassenliste);
        aktienlisteListView.setAdapter(KlassenListeAdapter);

        return rootView;

    }
}
