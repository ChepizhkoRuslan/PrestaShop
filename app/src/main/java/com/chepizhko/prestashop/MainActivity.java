package com.chepizhko.prestashop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.chepizhko.prestashop.adapter.PrestaAdapter;
import com.chepizhko.prestashop.model.ImageItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chepizhko.prestashop.App.getAuthToken;

public class MainActivity extends AppCompatActivity{
    public final static String TAG = "myLogs";
    List<ImageItem> imageItems = new ArrayList<>();
    private List<String> id_default_image = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> reference = new ArrayList<>();
    private List<String> price = new ArrayList<>();
    public static int countRequest = 0;
    private RecyclerView rv;
    public static boolean loading = true;
    PrestaAdapter mAdapter;
    ParseTask mParseTask;
    Call<ResponseBody> resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (loading) {
                    if (dy > 0) //check for scroll down
                    {
                        int visibleItemCount = llm.getChildCount();
                        int totalItemCount = llm.getItemCount();
                        int pastVisiblesItems = llm.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", " Reached Last Item");
                            countRequest = countRequest + 20;
                            imageItems.clear();
                            setRequest();
                        }
                    }
                }
            }
        });

        setRequest();
    }

    void setRequest(){
        // get resp
        resp = App.getService().callBack(App.getAuthToken(),""+countRequest+",20");
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d(TAG, "RESPONSE ====== " + response + "==============" + getAuthToken());
                Toast.makeText(MainActivity.this, "countRequest "+countRequest , Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
//                    new ParseTask(response.body()).execute();
                    if(mParseTask == null){
                         mParseTask = new ParseTask(response.body());
                         mParseTask.execute();
                    }

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

    private class ParseTask extends AsyncTask<Void,Void,List<ImageItem>> {
        private ResponseBody response;
        // конструктор, который получает response и сохраняет ее в переменной
        ParseTask(ResponseBody query) {
            response = query;
        }
        // метод doInBackground(…) для парсинга данных с сайта
        @Override
        protected List<ImageItem> doInBackground(Void... params) {
            try {
                Document document = Jsoup.parse(response.string());
                Log.e(TAG, "==========="+document);

                Elements elements1 = document.select("reference");
                for(Element link : elements1){
                    reference.add(link.text());
                }

                Elements elements2 = document.select("price");
                for(Element link : elements2){
                    price.add(link.text());
                }

                Elements elements3 = document.select("language[id=1]");
                int a = 0;
                for(Element link : elements3){
                    a++;
                    if(a%2 == 1) {
                        name.add(link.text());
                    }else{
                        String elem = link.text().replace("<p>","");
                        elem = elem.replace("</p>","");
                        description.add(elem);
                    }
                }
                for(int i = 0; i< elements1.size(); i++){
                    String elem = document.select("id_default_image").get(i).attr("xlink:href");
                    id_default_image.add(elem);
                    Log.e(TAG, "=="+elem);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < description.size(); i++){
                imageItems.add( new ImageItem( id_default_image.get(i),
                        name.get(i),description.get(i),reference.get(i), price.get(i)));
            }
            return imageItems;
        }
        @Override
        protected void onPostExecute(List<ImageItem> items) {
            imageItems = items;
            if(mAdapter==null) {
                mAdapter = new PrestaAdapter(getApplicationContext(), imageItems);
                rv.setAdapter(mAdapter);
            }
            else  {
//                mAdapter.notifyItemChanged(countRequest+1);
                mAdapter.notifyItemInserted(countRequest+1);
                rv.scrollToPosition(countRequest+1);
            }
//            mParseTask.cancel(false);
            mParseTask = null;
            resp = null;
        }
    }
}