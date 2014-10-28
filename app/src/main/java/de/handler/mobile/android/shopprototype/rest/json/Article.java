package de.handler.mobile.android.shopprototype.rest.json;

import android.os.Parcel;
import android.os.Parcelable;

import de.handler.mobile.android.shopprototype.rest.json.model.article.FairmondoDonation;
import de.handler.mobile.android.shopprototype.rest.json.model.article.FairmondoSeller;
import de.handler.mobile.android.shopprototype.rest.json.model.article.FairmondoTag;

/**
 * Model for Rest Communication
 * Represents the structure returned
 * as json by Fairmondo server
 */
public class Article implements Parcelable {
    private Long id;
    private String slug;
    private String title_image_url;
    private String html_url;
    private String title;
    private int price_cents;
    private int vat;
    private int basic_price_cents;
    private int basic_price_amount;
    private FairmondoTag tags;
    private FairmondoSeller seller;
    private FairmondoDonation donation;
    //TODO count added manually - could cause trouble when sending to server
    private int count = 0;

    public Article(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle_image_url() {
        return title_image_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice_cents() {
        return price_cents;
    }

    public int getVat() {
        return vat;
    }

    public int getBasic_price_cents() {
        return basic_price_cents;
    }

    public int getBasic_price_amount() {
        return basic_price_amount;
    }

    public FairmondoTag getTags() {
        return tags;
    }

    public FairmondoSeller getSeller() {
        return seller;
    }

    public FairmondoDonation getDonation() {
        return donation;
    }

    public Article() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTitle_image_url(String title_image_url) {
        this.title_image_url = title_image_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice_cents(int price_cents) {
        this.price_cents = price_cents;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public void setBasic_price_cents(int basic_price_cents) {
        this.basic_price_cents = basic_price_cents;
    }

    public void setBasic_price_amount(int basic_price_amount) {
        this.basic_price_amount = basic_price_amount;
    }

    public void setTags(FairmondoTag tags) {
        this.tags = tags;
    }

    public void setSeller(FairmondoSeller seller) {
        this.seller = seller;
    }

    public void setDonation(FairmondoDonation donation) {
        this.donation = donation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.title_image_url);
        dest.writeString(this.html_url);
        dest.writeString(this.title);
        dest.writeInt(this.price_cents);
        dest.writeInt(this.vat);
        dest.writeInt(this.basic_price_cents);
        dest.writeInt(this.basic_price_amount);
        dest.writeParcelable(this.tags, 0);
        dest.writeParcelable(this.seller, 0);
        dest.writeParcelable(this.donation, 0);
        dest.writeInt(this.count);
    }

    private Article(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.slug = in.readString();
        this.title_image_url = in.readString();
        this.html_url = in.readString();
        this.title = in.readString();
        this.price_cents = in.readInt();
        this.vat = in.readInt();
        this.basic_price_cents = in.readInt();
        this.basic_price_amount = in.readInt();
        this.tags = in.readParcelable(FairmondoTag.class.getClassLoader());
        this.seller = in.readParcelable(FairmondoSeller.class.getClassLoader());
        this.donation = in.readParcelable(FairmondoDonation.class.getClassLoader());
        this.count = in.readInt();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}