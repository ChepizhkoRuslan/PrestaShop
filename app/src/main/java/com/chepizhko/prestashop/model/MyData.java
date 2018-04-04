package com.chepizhko.prestashop.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "prestashop",strict = false)
public class MyData {

    @Root(name = "products",strict = false)
    public static class Product2{

        @Element(name = "id_default_image",required = false)
        public String id_default_image;

        public String getId_default_image() {
            return id_default_image;
        }
    }

}
