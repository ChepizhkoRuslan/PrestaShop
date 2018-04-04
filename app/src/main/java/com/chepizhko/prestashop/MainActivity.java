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
import com.chepizhko.prestashop.model.PrestaShop;

import org.leibnizcenter.xml.TerseJson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static String KEY = "XHKM6A6BLCA5MNYZQBX2GXBAAKSTPMK2";
    public final static String TAG = "myLogs";
    private List<ImageItem> items;;
    private List<String> descriptions;
    private RecyclerView rv;

    private static final TerseJson.WhitespaceBehaviour COMPACT_WHITE_SPACE = TerseJson.WhitespaceBehaviour.Compact;

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
        Call<PrestaShop> resp = service.callBack(getAuthToken());
        resp.enqueue(new Callback<PrestaShop>() {
            @Override
            public void onResponse(@NonNull Call<PrestaShop> call, @NonNull Response<PrestaShop> response) {
                Log.d(TAG, "RESPONSE ====== " + response+ "=============="+getAuthToken());
                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();
//                try {
                    PrestaShop data = response.body();


                    data.getProductList().forEach(
                            product ->
                    Log.d(TAG, "response.body() ==== " + product.getId_default_image() + " Link: " + product.getPrice()));

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
            public void onFailure(@NonNull Call<PrestaShop> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });




//        new NewAsyncTask().execute();

        initData();
        initAdapter();

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
