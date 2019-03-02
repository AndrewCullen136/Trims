package ie.cm.main;

import ie.cm.db.DBManager;

import android.app.Application;
import android.util.Log;

public class TrimsApp extends Application
{
    //public List <Trim>  trimList = new ArrayList<Trim>();
    public DBManager dbManager;


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("trim", "Trims App Started");
        dbManager = new DBManager(this);
        dbManager.open();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        dbManager.close();
    }
}
