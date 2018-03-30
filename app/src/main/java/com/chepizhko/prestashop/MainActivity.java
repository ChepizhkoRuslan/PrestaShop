package com.chepizhko.prestashop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chepizhko.prestashop.adapter.PrestaAdapter;
import com.chepizhko.prestashop.api.APIService;
import com.chepizhko.prestashop.model.ImageItem;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "myLogs";
    private List<ImageItem> items;
    private RecyclerView rv;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ps1722.weeteam.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        final APIService service = retrofit.create(APIService.class);
        Call<JsonObject> resp = service.callBack();
        resp.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "RESPONSE ===================== " + response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        initData();
        initAdapter();

    }

    private void initData() {
        items = new ArrayList<>();
        items.add(new ImageItem("http://ps1722.weeteam.net/api/images/products/1/1","yyyy","uuuuuuuu","iiiiiiiiiiiiii", "900$"));
        items.add(new ImageItem("http://ps1722.weeteam.net/api/images/products/1/1","yyyy","uuuuuuuu","iiiiiiiiiiiiii", "00000$"));
        items.add(new ImageItem("https://www.simplifiedcoding.net/demos/marvel/ironman.jpg","yyyy","uuuuuuuu","iiiiiiiiiiiiii", "343400$"));


    }
    private void initAdapter() {
        PrestaAdapter adapter = new PrestaAdapter(getApplicationContext(), items);
        rv.setAdapter(adapter);
    }
}
