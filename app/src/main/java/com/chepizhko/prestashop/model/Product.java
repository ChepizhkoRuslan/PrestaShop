package com.chepizhko.prestashop.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="product", strict=false)
public class Product {

    @Element(name = "id_default_image")
    String id_default_image;

//    @Element(name = "language")
//    @Path("name")
//    String langName;
//
//    @Element(name = "language")
//    @Path("description")
//    String langDescription;

    @Element(name = "reference")
    String reference;

    @Element(name = "price")
    String price;

//    public Product() {
//
//    }

    public Product(String id_default_image, String reference, String price) {
        this.id_default_image = id_default_image;
        this.reference = reference;
        this.price = price;
    }

    public String getId_default_image() {
        return id_default_image;
    }

    public void setId_default_image(String id_default_image) {
        this.id_default_image = id_default_image;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
