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
    private List<ImageItem> imageItems = new ArrayList<>();
    private List<String> id_default_image = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> reference = new ArrayList<>();
    private List<String> price = new ArrayList<>();
    private RecyclerView rv;
    Retrofit retrofit;

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

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ps1722.weeteam.net/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        setRequest();

    }

    void setRequest(){
        final APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> resp = service.callBack(getAuthToken());
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d(TAG, "RESPONSE ====== " + response + "==============" + getAuthToken());

                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();

                    try {
                        Document document = Jsoup.parse(response.body().string());
                        Log.e(TAG, "==========="+document);

                        Elements elements1 = document.select("reference");
                        int a = 0;
                        for(Element link : elements1){
                            a++; if(a>countResponse)break;
                            reference.add(link.text());
                        }

                        Elements elements2 = document.select("price");
                        int b = 0;

                        for(Element link : elements2){
                            b++; if(b>countResponse)break;
                            price.add(link.text());

                        }

                        Elements elements3 = document.select("language[id=1]");
                        int i3 = 0;
                        for(Element link : elements3){
                            i3++; if(i3>countResponse+countResponse)break;
                            if(i3%2 == 1) {
                                name.add(link.text());
                            }else{
                                String elem = link.text().replace("<p>","");
                                elem = elem.replace("</p>","");
                                description.add(elem);
                            }
                        }

                        for(int i = 0; i< countResponse; i++){
                            String elem = document.select("id_default_image").get(i).attr("xlink:href");
                            id_default_image.add(elem);
                        }

                    }catch (IOException e) {
                        e.printStackTrace();
                    }

//                    int count = 0;
//
//                    for(int i=0; i < description.size();i++){
//                        count++;
//                        Toast.makeText(MainActivity.this, "Pars "+count+"  -  "+description.get(i) , Toast.LENGTH_SHORT).show();
//
//                    }

                    for (int i = 0; i < description.size(); i++){
                        imageItems.add( new ImageItem( "http://life-instyle.com/index.php?option=com_joomgallery&view=image&format=raw&id=30928&type=img",
                                name.get(i),description.get(i),reference.get(i), price.get(i)));
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

}