package ie.cm.adapters;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ie.cm.db.DBManager;
import ie.cm.models.Trim;
import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class TrimFilter extends Filter {
    private OrderedRealmCollection<Trim> originalTrimList;
    private RealmResults<Trim> realmTrimResults;
    public TrimListAdapter adapter;
    private boolean favourites = false;
    private DBManager dbManager;



    public TrimFilter(DBManager dbManager, TrimListAdapter adapter) {
        super();
        this.dbManager = dbManager;
        this.originalTrimList = dbManager.getAll();
        this.adapter = adapter;
    }

    public void setFilter(String filterText) {
        favourites = !filterText.equals("all");
    }

    @Override
    protected FilterResults performFiltering(CharSequence prefix) {
        return new FilterResults();
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence prefix, FilterResults results) {

        if ((prefix == null || prefix.length() == 0))
            if(!favourites)
                realmTrimResults = dbManager.getAll();
            else
                realmTrimResults = dbManager.getFavourites();
        else {
            realmTrimResults = dbManager.realmDatabase
                    .where(Trim.class)
                    .equalTo("favourite", favourites)
                    .contains("name", prefix.toString(), Case.INSENSITIVE)
                    .or()
                    .contains("shop", prefix.toString(), Case.INSENSITIVE)
                    .findAll();
        }

        adapter.trimList = realmTrimResults;

        if (adapter.trimList.size() > 0)
            adapter.notifyDataSetChanged();
        else {
            adapter.notifyDataSetInvalidated();
            adapter.trimList = originalTrimList;
        }
    }
}
