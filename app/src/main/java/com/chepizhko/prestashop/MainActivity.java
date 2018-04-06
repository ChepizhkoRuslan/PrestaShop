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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

    private List<ImageItem> imageItems = new ArrayList<>();
    private List<String> id_default_image = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> reference = new ArrayList<>();
    private List<String> price = new ArrayList<>();

    Elements elements;

    public static int countResponse = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);

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

//                    //XmlPullParser для парсинга урла фото - переделать на JSOUP!!!
//                    try {
//                        parseXmlResponse(response.body().string());
//                    } catch (XmlPullParserException | IOException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        Document document = Jsoup.parse(response.body().string());
                        Log.e(TAG, "==========="+document);

                        elements = document.select("reference");
                        int i = 0;
                        for(Element link : elements){
                            i++; if(i>countResponse)break;
                            reference.add(link.text());
//                            Log.e(TAG, "===========reference============"+link.text());
                        }

                        elements = document.select("price");
                        int b = 0;
                        for(Element link : elements){
                            b++; if(i>countResponse)break;
                            price.add(link.text());
//                            Log.e(TAG, "===========price============"+link.text());
                        }

                        elements = document.select("language[id=1]");
                        int i3 = 0;
                        for(Element link : elements){
                            i3++; if(i>countResponse+countResponse)break;
                            if(i3%2 == 1) {
                                name.add(link.text());
                                Log.e(TAG, "=======================" + link.text());
                            }else{
                                description.add(link.text());
                                Log.e(TAG, "=======================" + link.text());
                            }


                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    int count = 0;

//                    for(int i=0; i < description.size();i++){
//                        count++;
//                        Toast.makeText(MainActivity.this, "Pars "+count+"  -  "+description.get(i) , Toast.LENGTH_SHORT).show();
//
//                    }

                    //items = ReworkList.reworkList(getApplicationContext(), id_default_image, reference ,price,name ,description );

                    for (int i = 0; i < description.size(); i++){
                        imageItems.add(new ImageItem("http://s1.1zoom.net/big0/994/298568-alexfas01.jpg", name.get(i),description.get(i),reference.get(i), price.get(i)));
                    }

                    rv.setAdapter(new PrestaAdapter(getApplicationContext(), imageItems));

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

    }

//    private void parseXmlResponse(String RESPONSE_STRNG_HERE) throws XmlPullParserException, IOException {
//
//        XmlPullParserFactory factory = null;
//        try {
//            factory = XmlPullParserFactory.newInstance();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        if (factory != null) {
//            factory.setNamespaceAware(true);
//        }
//        XmlPullParser xpp = null;
//        if (factory != null) {
//            xpp = factory.newPullParser();
//        }
//        if (xpp != null) {
//            xpp.setInput(new StringReader(RESPONSE_STRNG_HERE));
//        }
//        int eventType = xpp.getEventType();
//        while (eventType != XmlPullParser.END_DOCUMENT) {
//            if (eventType == XmlPullParser.START_DOCUMENT) {
//                System.out.println("Start document");
//            } else if (eventType == XmlPullParser.START_TAG) {
//                System.out.println("Start tag " + xpp.getName());
//                Log.e("Count ----------", "" + xpp.getAttributeCount());
//
//                int ia;
//                for (ia = 0; ia < xpp.getAttributeCount(); ia++) {
//
//                    if(xpp.getName().equals("id_default_image")) {
//                            if (ia % 2 == 0) {
//                                id_default_image.add(xpp.getAttributeValue(ia));
//                                Log.e(TAG, "====000000000000000000000"+xpp.getAttributeValue(ia));
//                            }
//                    }
//                }
//
//            } else if (eventType == XmlPullParser.END_TAG) {
//                System.out.println("End tag ------------- " + xpp.getName());
//            } else if (eventType == XmlPullParser.TEXT) {
//
//                Log.e(TAG, "TEXT==="+xpp.getText());
//
//            }
//            eventType = xpp.next();
//        }
//    }


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
}