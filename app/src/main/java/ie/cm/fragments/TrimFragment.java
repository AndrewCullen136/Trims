package ie.cm.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


import ie.cm.activities.Base;
import ie.cm.adapters.TrimFilter;
import ie.cm.adapters.TrimListAdapter;
import ie.cm.models.Trim;
import ie.cm.trims.R;

public class TrimFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener {

    public Base activity;
    public static TrimListAdapter listAdapter;
    public ListView listView;
    public TrimFilter trimFilter;
    public boolean favourites = false;


    public TrimFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("cutId", (String) view.getTag());

        Fragment fragment = EditFragment.newInstance(activityInfo);
        getActivity().setTitle(R.string.editTrimLbl);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static Fragment newInstance() {
        TrimFragment trFrag = new TrimFragment();
        return trFrag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        getActivity().setTitle(R.string.recentlyViewedLbl);
        listAdapter = new TrimListAdapter(activity, this, activity.app.dbManager.getAll());
        trimFilter = new TrimFilter(activity.app.dbManager , listAdapter);

        if (favourites) {
            getActivity().setTitle(R.string.favouritesHaircutsLbl);
            trimFilter.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
            trimFilter.filter(null); // Filter the data, but don't use any prefix
            listAdapter.notifyDataSetChanged(); // Update the adapter
        }
        // setRandomCoffee();

        listView = v.findViewById(R.id.homeList);

        setListView(v);

        return v;
    }

    public void setListView(View view) {
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        listView.setEmptyView(view.findViewById(R.id.emptyList));
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Trim) {
            onTrimDelete((Trim) view.getTag());
        }
    }

    public void onTrimDelete(final Trim trim) {
        String stringName = trim.cutStyle;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Trim\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.app.dbManager.delete(trim.cutId); // remove from our list
                listAdapter.trimList.remove(trim); // update adapters data
                listAdapter.notifyDataSetChanged(); // refresh adapter
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_delete_trim:
                deleteTrims(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteTrims(ActionMode actionMode) {
        for (int i = listAdapter.getCount() - 1; i >= 0; i--) {
            if (listView.isItemChecked(i)) {
                activity.app.dbManager.delete(listAdapter.getItem(i).cutId);
                if (favourites)
                    listAdapter.trimList.remove(listAdapter.getItem(i));
            }
        }

        actionMode.finish();

        if (favourites) {
            //Update the filters data
            trimFilter = new TrimFilter(activity.app.dbManager,listAdapter);
            trimFilter.setFilter("favourites");
            trimFilter.filter(null);
        }
        listAdapter.notifyDataSetChanged();

    }



    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
    }




    /* ************ MultiChoiceModeListener methods (end) *********** */
}



