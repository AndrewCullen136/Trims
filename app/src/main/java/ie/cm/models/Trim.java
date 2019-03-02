package ie.cm.models;


import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Trim extends RealmObject {

    @PrimaryKey
    public String cutId;
    public String cutStyle;
    public String shop;
    public double rating;
    public double price;
    public boolean favourite;

    public Trim() {}

    public Trim(String Style, String shop, double rating, double price, boolean fav)
    {
        this.cutId = UUID.randomUUID().toString();
        this.cutStyle = Style;
        this.shop = shop;
        this.rating = rating;
        this.price = price;
        this.favourite = fav;
    }

    @Override
    public String toString() {
        return cutId + " " + cutStyle + ", " + shop + ", " + rating
                + ", " + price + ", fav =" + favourite;
    }

}
