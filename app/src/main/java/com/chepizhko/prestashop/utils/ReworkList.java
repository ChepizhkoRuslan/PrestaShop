package com.chepizhko.prestashop.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.chepizhko.prestashop.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

import static com.chepizhko.prestashop.MainActivity.TAG;

public class ReworkList {
//    private List<ImageItem> items;
//    private List<String> text = new ArrayList<>();
//    private List<String> id_default_image = new ArrayList<>();

    public static List<ImageItem> reworkList(Context context, List<ImageItem> imageItems, List<String> id_default_image, List<String> text) {
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

                Log.e(TAG,"Text==========reference============"+ text.get(i));

            }
            else if (count == 1) {
                price.add(text.get(i));
                                Log.e(TAG,"Text===========price==========="+ text.get(i));

            }
            else if (count == 2) {
                name.add(text.get(i));
                Log.e(TAG,"Text===========name==========="+ text.get(i));

            }
            else if (count == 4) {
                description.add(text.get(i));
                Log.e(TAG,"Text===========description==========="+ text.get(i));

            }

            count++;
        }






        for (String idi : name){
                        Toast.makeText(context, "id = "+ idi, Toast.LENGTH_SHORT).show();

                    }


        return imageItems;
    }

}
