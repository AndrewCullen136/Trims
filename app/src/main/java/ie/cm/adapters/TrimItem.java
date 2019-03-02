package ie.cm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ie.cm.models.Trim;
import ie.cm.trims.R;

public class TrimItem {

    View view;

    public TrimItem(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, Trim trim)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.trimrow, parent, false);
        view.setTag(trim.cutId);

        updateControls(trim);

        ImageView imgDelete =  view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(trim);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Trim trim) {
        ((TextView) view.findViewById(R.id.rowCutStyle)).setText(trim.cutStyle);

        ((TextView) view.findViewById(R.id.rowBarberShop)).setText(trim.shop);
        ((TextView) view.findViewById(R.id.rowRating)).setText(trim.rating +" *");
        ((TextView) view.findViewById(R.id.rowPrice)).setText( " €"+
                new DecimalFormat("0.00").format(trim.price));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if(trim.favourite)
            imgIcon.setImageResource(R.drawable.favourites_72_on);
        else
            imgIcon.setImageResource(R.drawable.favourites_72);


    }
}
