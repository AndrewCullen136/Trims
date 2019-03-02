package ie.cm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;


import ie.cm.activities.Home;
import ie.cm.main.TrimsApp;
import ie.cm.models.Trim;
import ie.cm.trims.R;

public class AddFragment extends Fragment {

    private String 		cutStyle, barberShop;
    private double 		cutPrice, ratingValue;
    private EditText style, shop, price;
    private RatingBar ratingBar;
    private ImageButton saveButton;
    private TrimsApp app;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (TrimsApp) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        getActivity().setTitle(R.string.addHaircutBtnLbl);
        style = v.findViewById(R.id.addStyleET);
        shop =  v.findViewById(R.id.addShopET);
        price =  v.findViewById(R.id.addPriceET);
        ratingBar =  v.findViewById(R.id.addRatingBar);
        saveButton = v.findViewById(R.id.addTrimBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCoffee();
            }
        });

        return v;
    }

    public void addCoffee() {

        cutStyle = style.getText().toString();
        barberShop = shop.getText().toString();
        try {
            cutPrice = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e) {
            cutPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if ((cutStyle.length() > 0) && (barberShop.length() > 0)
                && (price.length() > 0)) {
            Trim t = new Trim(cutStyle, barberShop, ratingValue,
                    cutPrice, false);

            app.dbManager.add(t);
            startActivity(new Intent(this.getActivity(), Home.class));
        } else
            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Shop\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }
}