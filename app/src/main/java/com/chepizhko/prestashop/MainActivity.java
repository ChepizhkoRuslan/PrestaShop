package com.chepizhko.prestashop;

import android.os.AsyncTask;
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
    private List<ImageItem> items;;
    //private List<String> descriptions;
    private RecyclerView rv;


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
                Log.d(TAG, "RESPONSE ====== " + response+ "=============="+getAuthToken());
                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();

                    try {
                        parseXmlResponse(response.body().string());
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }


                    /// перевод хмл в стринг
//                try {

//                    String str = response.body().toString();
//
//                    Document doc = DomHelper.parse(String.valueOf(str));
//
//                    // Convert DOM to terse representation, and convert to JSON
//                    TerseJson.Options opts = TerseJson.Options
//                            .with(COMPACT_WHITE_SPACE)
//                            .and(TerseJson.ErrorBehaviour.ThrowAllErrors);
//
//                    Object terseDoc = new TerseJson(opts).convert(doc);
//                    String json = new Gson().toJson(terseDoc);
//                    //Log.d(TAG, "JSONObject ================= " + json);
//
//
//
//                    JSONObject jsonBody = new JSONObject(json);
//
//                    // метод вызывает parseItems(…) и возвращает List с объектами GalleryItem
//                    parseItems(items, jsonBody);




//                } catch (IOException e) {
//                    e.printStackTrace();
//                }catch (NullPointerException e) {
//                    e.printStackTrace();
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                } catch (NotImplemented notImplemented) {
//                    notImplemented.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                }else {
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
        List<String> name = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> reference = new ArrayList<>();
        List<String> id_default_image = new ArrayList<>();
        List<String> price = new ArrayList<>();

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
                Log.e("Count ", "" + xpp.getAttributeCount());



                for (int ia = 0; ia < xpp.getAttributeCount(); ia++) {
                    switch (xpp.getName()) {


                        case "name":
                            if(xpp.getName().equals("language")){
                                if (xpp.getAttributeValue(null, "id").equals("1")) {
                                    Toast.makeText(this, "-----------------------"+xpp.getAttributeValue(ia), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "++++++++++++++++++++++++++++++++" + xpp.getAttributeValue(ia));
                                    break;
                                }
                            }

                            name.add(xpp.getAttributeValue(ia));

                            break;
                        case "description":
                            description.add(xpp.getAttributeValue(ia));
                            break;
                        case "reference":
                            description.add(xpp.getAttributeValue(ia));
                            break;
                        case "id_default_image":
                            description.add(xpp.getAttributeValue(ia));
                            break;
                        case "price":
                            description.add(xpp.getAttributeValue(ia));
                            break;
                    }
                    Log.e("Attribute Name ", xpp.getAttributeName(ia));
                    Log.e("Attribute Value ", xpp.getAttributeValue(ia));

                }

            } else if (eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag " + xpp.getName());
            } else if (eventType == XmlPullParser.TEXT) {
                Log.e("Text ", xpp.getText());
            }
            eventType = xpp.next();
        }
    }

//    private void parseItems(List<ImageItem> items, JSONObject jsonBody) throws IOException, JSONException{
//        JSONObject itemJsonObject = jsonBody.getJSONObject("products");
//        JSONArray itemJsonArray = itemJsonObject.getJSONArray("product");
//        for (int i = 0; i < itemJsonArray.length(); i++) {
//            JSONObject photoJsonObject = itemJsonArray.getJSONObject(i);
//            ImageItem item = new ImageItem();
//            Toast.makeText(this, "RESPONSE  " + photoJsonObject.getString("id_default_image"), Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "RESPONSE ================== " + photoJsonObject.getString("id_default_image"));
//            item.setId_default_image(photoJsonObject.getString("id_default_image"));
//            item.setName(photoJsonObject.getString("name"));
//            item.setDescription(photoJsonObject.getString("description"));
//            item.setReference(photoJsonObject.getString("reference"));
//            item.setPrice(photoJsonObject.getString("price"));
//            items.add(item);
//        }
//    }

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

    private class NewAsyncTask extends AsyncTask<String, String,List<ImageItem>>{

        @Override
        protected List<ImageItem>  doInBackground(String... strings) {

            return new ParseJson().getItems();
        }

        @Override
        protected void onPostExecute(List<ImageItem> its) {
            items = its;
            initAdapter();
        }
    }
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
