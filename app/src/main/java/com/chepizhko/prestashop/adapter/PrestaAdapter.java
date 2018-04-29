package com.chepizhko.prestashop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chepizhko.prestashop.App;
import com.chepizhko.prestashop.PrestaItemActivity;
import com.chepizhko.prestashop.R;
import com.chepizhko.prestashop.model.ImageItem;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.chepizhko.prestashop.MainActivity.loading;

public class PrestaAdapter extends RecyclerView.Adapter<PrestaAdapter.PrestaViewHolder> {

    private Context mContext;

    public class PrestaViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private ImageView default_image;
        private TextView name;
        private TextView description;
        private TextView reference;
        private TextView price;
        private LinearLayout mLayout;
        Picasso picasso = new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(App.getClient())).build();

        PrestaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            default_image = (ImageView) itemView.findViewById(R.id.id_default_image);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            reference = (TextView)itemView.findViewById(R.id.reference);
            price = (TextView)itemView.findViewById(R.id.price);
            mLayout = (LinearLayout)itemView.findViewById(R.id.linear_item);
        }

        private void bindGalleryItem(ImageItem imageItem) {

            name.setText(imageItem.getName());
            description.setText(imageItem.getDescription());
            reference.setText(imageItem.getReference());
            price.setText(imageItem.getPrice());

            picasso.load(imageItem.getId_default_image()).
                    resize(70, 80).
                    into(default_image);
        }

    }

    private List<ImageItem> items;

    public PrestaAdapter(Context context, List<ImageItem> items){
        this.mContext = context;
        this.items = items;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PrestaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item, viewGroup, false);
        PrestaViewHolder pvh = new PrestaViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PrestaViewHolder prestaViewHolder, @SuppressLint("RecyclerView") int i) {
        ImageItem imageItem = items.get(i);
        prestaViewHolder.bindGalleryItem(imageItem);
        prestaViewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "YYYYYYYY"+ imageItem.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = PrestaItemActivity.newIntent(mContext, imageItem);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        if(i<items.size()){
            loading = true;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

