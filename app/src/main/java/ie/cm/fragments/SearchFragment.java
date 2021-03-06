package ie.cm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import ie.cm.trims.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends TrimFragment implements AdapterView.OnItemSelectedListener {

    String selected;
    SearchView searchView;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().setTitle(R.string.searchHaircutsLbl);
        listView = v.findViewById(R.id.searchList); //Bind to the list on our Search layout
        setListView(v);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.trimTypes,
                        android.R.layout.simple_spinner_item);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = v.findViewById(R.id.searchSpinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        searchView = v.findViewById(R.id.searchView);
        searchView.setQueryHint("Search your Coffees Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                trimFilter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                trimFilter.filter(newText);
                return false;
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context c) { super.onAttach(c); }

    @Override
    public void onStart() {
        super.onStart();
    }


    private void checkSelected(String selected)
    {
        if (selected != null) {
            if (selected.equals("All Types")) {
                trimFilter.setFilter("all");
            } else if (selected.equals("Favourites")) {
                trimFilter.setFilter("favourites");
            }

            String filterText = ((SearchView)activity
                    .findViewById(R.id.searchView)).getQuery().toString();

            if(filterText.length() > 0)
                trimFilter.filter(filterText);
            else
                trimFilter.filter("");
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        checkSelected(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void deleteTrims(ActionMode actionMode) {
        super.deleteTrims(actionMode);
        checkSelected(selected);
    }





}
