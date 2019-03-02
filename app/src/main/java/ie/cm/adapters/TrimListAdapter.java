package ie.cm.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.cm.models.Trim;
import ie.cm.trims.R;

public class TrimListAdapter  extends ArrayAdapter<Trim> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<Trim> trimList;

    public TrimListAdapter(Context context, View.OnClickListener deleteListener, List<Trim> trimList)
    {
        super(context , R.layout.trimrow , trimList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.trimList = trimList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TrimItem item = new TrimItem(context, parent,
                deleteListener , trimList.get(position));
        return item.view;
    }

    @Override
    public int getCount()
    {
        return trimList.size();
   }

  //  @Override
  //  public Trim getItem(int position) {
  //      return trimList.get(position);
    // }




}
