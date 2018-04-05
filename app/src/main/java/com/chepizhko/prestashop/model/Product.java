package com.chepizhko.prestashop.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="product", strict=false)
public class Product {

    @Element(name = "id_default_image")
    String id_default_image;

    @Element(name = "reference")
    String reference;

    @Element(name = "price")
    String price;


}
