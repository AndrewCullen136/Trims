package ie.cm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;



import ie.cm.main.TrimsApp;

import ie.cm.models.Trim;
import ie.cm.trims.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    public boolean isFavourite;
    public Trim aTrim;
    public ImageView editFavourite;
    private EditText style, shop, price;
    private RatingBar ratingBar;
    public TrimsApp app;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Bundle coffeeBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(coffeeBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (TrimsApp) getActivity().getApplication();

        if(getArguments() != null)
            aTrim = app.dbManager.get(getArguments().getString("cutId"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        ((TextView)v.findViewById(R.id.editTitleTV)).setText(aTrim.cutStyle);

        style = v.findViewById(R.id.editStyleET);
        shop = v.findViewById(R.id.editShopET);
        price = v.findViewById(R.id.editPriceET);
        ratingBar = v.findViewById(R.id.editRatingBar);
        editFavourite = v.findViewById(R.id.editFavourite);

        style.setText(aTrim.cutStyle);
        shop.setText(aTrim.shop);
        price.setText(""+aTrim.price);
        ratingBar.setRating((float)aTrim.rating);

        if (aTrim.favourite == true) {
            editFavourite.setImageResource(R.drawable.favourites_72_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.favourites_72);
            isFavourite = false;
        }
        return v;
    }

    public void saveCoffee(View v) {
        if (mListener != null) {
            String cutStyle = style.getText().toString();
            String barberShop = shop.getText().toString();
            String cutPriceStr = price.getText().toString();
            double ratingValue = ratingBar.getRating();

            double coffeePrice;
            try {
                coffeePrice = Double.parseDouble(cutPriceStr);
            } catch (NumberFormatException e)
            {            coffeePrice = 0.0;        }

            if ((cutStyle.length() > 0) && (barberShop.length() > 0) && (cutPriceStr.length() > 0)) {
                app.dbManager.update(aTrim,cutStyle,barberShop,coffeePrice,ratingValue);

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    return;
                }
            }
        } else
            Toast.makeText(getActivity(), "You must Enter Something for Name and Shop", Toast.LENGTH_SHORT).show();
    }

    public void toggle(View v) {

        if (isFavourite) {
            app.dbManager.setFavourite(aTrim , false);
            Toast.makeText(getActivity(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.favourites_72);
        } else {
            app.dbManager.setFavourite(aTrim , true);
            Toast.makeText(getActivity(), "Added to Favourites !!", Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.favourites_72_on);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void toggle(View v);
        void saveCoffee(View v);
    }
}
