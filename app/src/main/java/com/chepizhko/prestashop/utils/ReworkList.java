package com.chepizhko.prestashop.utils;

import android.content.Context;

import com.chepizhko.prestashop.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ReworkList {

    public static List<ImageItem> reworkList(Context context, List<String> id_image, List<String> text) {
        List<ImageItem> imageItems = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> reference = new ArrayList<>();
        List<String> price = new ArrayList<>();


        int count = 0;
        for (int i = 0; i < text.size(); i++) {
            if(count == 6) {
                count = 0;

            }
            if (count == 0) {
                reference.add(text.get(i));
            }
            else if (count == 1) {
                price.add(text.get(i));
            }
            else if (count == 2) {
                name.add(text.get(i));
            }
            else if (count == 4) {
                description.add(text.get(i));
            }

            count++;
        }

        for (int i = 0; i < description.size(); i++){
             imageItems.add(new ImageItem(id_image.get(i), name.get(i),description.get(i),reference.get(i), price.get(i)));
        }

        return imageItems;
    }

}
