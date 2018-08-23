package com.chepizhko.prestashop.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Parcelable {

    private String id_default_image;
    private String name;
    private String description;
    private String reference;
    private String price;




    public ImageItem(String id_default_image, String name, String description, String reference, String price) {
        this.id_default_image = id_default_image;
        this.name = name;
        this.description = description;
        this.reference = reference;
        this.price = price;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    // упаковываем объект в Parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_default_image);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(reference);
        parcel.writeString(price);
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        // распаковываем объект из Parcel
        @Override
        public ImageItem createFromParcel(Parcel parcel) {
            return new ImageItem(parcel);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private ImageItem(Parcel parcel) {
        id_default_image = parcel.readString();
        name = parcel.readString();
        description = parcel.readString();
        reference = parcel.readString();
        price = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getId_default_image() {
        return id_default_image;
    }

    public void setId_default_image(String id_default_image) {
        this.id_default_image = id_default_image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
