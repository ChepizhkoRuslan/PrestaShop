package com.chepizhko.prestashop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.chepizhko.prestashop.adapter.PrestaAdapter;
import com.chepizhko.prestashop.api.APIService;
import com.chepizhko.prestashop.auth.BasicAuthInterceptor;
import com.chepizhko.prestashop.model.ImageItem;
import com.chepizhko.prestashop.utils.ReworkList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static String KEY = "XHKM6A6BLCA5MNYZQBX2GXBAAKSTPMK2";
    public final static String TAG = "myLogs";
    private RecyclerView rv;
    private List<ImageItem> items;
    private List<String> text = new ArrayList<>();
    private List<String> id_default_image = new ArrayList<>();

    private List<String> name = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> reference = new ArrayList<>();
    private List<String> price = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(getAuthToken()))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ps1722.weeteam.net/")
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addConverterFactory(new ToStringConverterFactory())
                .client(client)
                .build();


        final APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> resp = service.callBack(getAuthToken());
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d(TAG, "RESPONSE ====== " + response + "==============" + getAuthToken());
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();

                    //XmlPullParser

                    try {
                        parseXmlResponse(response.body().string());
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }

////                    while(id_default_image.remove("true")) {}
//                    for (String idi :id_default_image){
////                        Toast.makeText(MainActivity.this, "id = "+ idi, Toast.LENGTH_SHORT).show();
//
//                    }
//                    for (String idi :name){
////                        Toast.makeText(MainActivity.this, "id = "+ idi, Toast.LENGTH_SHORT).show();
//
//                    }
//                    for (String idi :description){
//                        Toast.makeText(MainActivity.this, "id = "+ idi, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    for (String idi :text){
//                        Toast.makeText(MainActivity.this, "id = "+ idi, Toast.LENGTH_SHORT).show();
//
//                    }

                    items = ReworkList.reworkList(getApplicationContext(),items,id_default_image,text);

                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });


//        new NewAsyncTask().execute();

        initData();
        initAdapter();

    }

    private void parseXmlResponse(String RESPONSE_STRNG_HERE) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        if (factory != null) {
            factory.setNamespaceAware(true);
        }
        XmlPullParser xpp = null;
        if (factory != null) {
            xpp = factory.newPullParser();
        }
        if (xpp != null) {
            xpp.setInput(new StringReader(RESPONSE_STRNG_HERE));
        }
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if (eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag " + xpp.getName());
//                Log.e("Count ----------", "" + xpp.getAttributeCount());

                int ia;
                for (ia = 0; ia < xpp.getAttributeCount(); ia++) {

                    switch (xpp.getName()) {
                        case "id_default_image":
                            if(ia%2==0) {
                                //Log.d(TAG, "getAttributeValue+++++++++++!!!!!!!!!!!++++++++++++++" + xpp.getAttributeValue(ia));
                            }
                             break;
//                        case "name":
//                            Log.d(TAG, "++++++++++++++++++++++++++++++" + xpp.getAttributeValue(ia));
//                            break;
//                        case "language":
//                            Log.d(TAG, "++++++++++++++++++++++++++++++" + xpp.nextText());
//                            break;
//                        case "description":
////                            xpp.getName().equalsIgnoreCase("__cdata");

//                            Log.d(TAG, "++++++++++++++++++++++++++++++++" + xpp.getAttributeValue(ia));
//                            break;
//                        case "reference":
//                            Log.d(TAG, "++++++++++++++++++++++++++++++++" + xpp.getAttributeValue(ia));
//                            break;
//                        case "price":
//                            Log.d(TAG, "++++++++++++++++++++++++++++++++" + xpp.getAttributeValue(ia));
//                            break;
                    }
//                    Log.e("Attribute ======Name ", xpp.getAttributeName(ia));
//                    Log.e("Attribute ======Value ", xpp.getAttributeValue(ia));
                }

            } else if (eventType == XmlPullParser.END_TAG) {
//                System.out.println("End tag ------------- " + xpp.getName());
            }
            else if (eventType == XmlPullParser.TEXT ) {

               if(xpp.getText().length() > 3  ){
                   // if (xpp.getText().matches("id =")) {

                        //Log.e(TAG, "Text======!!!!!!!!!!!!!!================" + xpp.getText());
                        text.add(xpp.getText());

                }
            }
            eventType = xpp.next();
        }
    }


    private void initAdapter() {
        rv.setAdapter(new PrestaAdapter(getApplicationContext(), items));
    }

    public static String getAuthToken() {
        byte[] data = new byte[0];
        try {
            data = (KEY + ":" + "").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }


//    private class NewAsyncTask extends AsyncTask<String, String,List<ImageItem>>{
//
//        @Override
//        protected List<ImageItem>  doInBackground(String... strings) {
//
//            return new ParserXmlAsyncTask().parseXmlResponse();
//        }
//
//        @Override
//        protected void onPostExecute(List<ImageItem> its) {
//            items = its;
//            initAdapter();
//        }
//    }
    private void initData() {
        items = new ArrayList<>();
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));
//        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","Fashion has been creating well-designed collections since 2010. The brand offers feminine designs delivering stylish separates and statement dresses which has since evolved into a full ready-to-wear collection in which every item is a vital part of a woman's wardrobe.","Ref: demo", "300$"));

    }

}
