package com.chepizhko.prestashop.utils;

import android.content.Context;

import com.chepizhko.prestashop.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ReworkList {

    public static List<ImageItem> reworkList(Context context, List<String> id_image, List<String> reference,List<String> price,List<String> name,List<String> description) {
        List<ImageItem> imageItems = new ArrayList<>();


        for (int i = 0; i < description.size(); i++){
             imageItems.add(new ImageItem("http://s1.1zoom.net/big0/994/298568-alexfas01.jpg", name.get(i),description.get(i),reference.get(i), price.get(i)));
        }

        return imageItems;
    }

}
