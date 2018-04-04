package com.chepizhko.prestashop.model;

public class ImageItem {

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
