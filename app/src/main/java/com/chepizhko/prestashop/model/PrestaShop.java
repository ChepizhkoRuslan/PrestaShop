package com.chepizhko.prestashop.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="prestashop", strict=false)
public class PrestaShop {

    @ElementList(name="product", inline=true)
    @Path("products")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public PrestaShop(List<Product> productList) {
        this.productList = productList;
    }


}
