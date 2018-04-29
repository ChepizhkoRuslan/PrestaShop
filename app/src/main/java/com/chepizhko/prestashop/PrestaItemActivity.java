package com.chepizhko.prestashop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.chepizhko.prestashop.model.ImageItem;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class PrestaItemActivity extends AppCompatActivity {

    private static final String ARG_IMAGE_ITEM = "image_item";
    private ImageView image;
    private TextView name;
    private TextView price;
    private TextView desc;
    private TextView refer;


    public static Intent newIntent(Context packageContext, ImageItem imageItem) {
        Intent intent = new Intent(packageContext, PrestaItemActivity.class);
        intent.putExtra(ARG_IMAGE_ITEM, imageItem);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presta_item);

        image = (ImageView)findViewById(R.id.presta_image);
        name = (TextView)findViewById(R.id.presta_name);
        price = (TextView)findViewById(R.id.presta_price);
        desc = (TextView)findViewById(R.id.presta_description);
        refer = (TextView)findViewById(R.id.presta_reference);

        ImageItem imageItem = (ImageItem) getIntent().getParcelableExtra(ARG_IMAGE_ITEM);
        name.setText(imageItem.getName());
        desc.setText(imageItem.getDescription());
        refer.setText(imageItem.getReference());
        price.setText(imageItem.getPrice());

            Picasso picasso = new Picasso.Builder(this)
            .downloader(new OkHttp3Downloader(App.getClient())).build();

        picasso.load(imageItem.getId_default_image()).
                resize(210, 240).
                into(image);
    }
}
