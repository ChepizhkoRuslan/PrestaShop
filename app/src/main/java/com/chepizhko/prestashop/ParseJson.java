package com.chepizhko.prestashop;

import com.chepizhko.prestashop.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ParseJson {

    public List<ImageItem> getItems() {
        return downloadImageItem();

    }

    // метод downloadItems(String) получает URL-адрес и загружает его содержимое
    private List<ImageItem> downloadImageItem() {
        List<ImageItem> items = new ArrayList<>();
      //  try {

            // получаем JSON из запроса по URL-адресу
            //String jsonString = getUrlString(url);
            //Log.i(TAG, "Received JSON: " + jsonString);
            // Тексты JSON легко разбираются в соответствующие объекты Java при помощи конструктора JSONObject(String)
            // и строит иерархию объектов, соответствующую исходному тексту JSON
            //JSONObject jsonBody = new JSONObject(jsonString);
            // метод вызывает parseItems(…) и возвращает List с объектами Item
           // parseItems(items, jsonBody);
//        } catch (IOException ioe) {
//            Log.e(TAG, "Failed to fetch items", ioe);
//        } catch (JSONException je){
//            Log.e(TAG, "Failed to parse JSON", je);
//        }
        return items;
    }
}
