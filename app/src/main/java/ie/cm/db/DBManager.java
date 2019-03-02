package ie.cm.db;

import android.content.Context;

import java.sql.SQLException;

import ie.cm.models.Trim;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DBManager {

    public Realm realmDatabase;

    public DBManager(Context context) {
        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("trim.realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    public void open() {
        realmDatabase = Realm.getDefaultInstance();
    }

    public void close() {
        realmDatabase.close();
    }

    public void add(Trim t) {
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealm(t);
        realmDatabase.commitTransaction();
    }

    public void update(Trim t, String style , String shop, double price , double rating)
    {
        realmDatabase.beginTransaction();
        t.cutStyle= style;
        t.shop = shop;
        t.price = price;
        t.rating = rating;
        realmDatabase.commitTransaction();
    }

    public void setFavourite(Trim t, boolean value)
    {
        realmDatabase.beginTransaction();
        t.favourite = value;
        realmDatabase.commitTransaction();
    }

    // If we wanted to update individual fields we could
    // do something like this, for example
    public void updateName(String style, String cutId)
    {
        Trim t = realmDatabase.where(Trim.class)
                .equalTo("cutId",cutId)
                .findFirst();
        realmDatabase.beginTransaction();
        t.cutStyle = style;
        realmDatabase.commitTransaction();
    }

    public void delete(String cutId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(Trim.class)
                .equalTo("cutId",cutId)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public RealmResults<Trim> getAll() {
        RealmResults<Trim> result = realmDatabase.where(Trim.class)
                .findAll();
        return result;
    }

    public RealmResults<Trim> getFavourites() {
        return realmDatabase.where(Trim.class)
                .equalTo("favourite",true)
                .findAll();
    }

    public Trim get(String cutId) {
        return realmDatabase.where(Trim.class)
                .equalTo("cutId",cutId)
                .findAll()
                .first();
    }

    public void reset() {
        realmDatabase.beginTransaction();
        realmDatabase.where(Trim.class)
                .findAll()
                .deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }
}
