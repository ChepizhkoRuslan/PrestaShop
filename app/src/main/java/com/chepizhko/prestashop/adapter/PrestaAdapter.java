package com.chepizhko.prestashop.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chepizhko.prestashop.App;
import com.chepizhko.prestashop.LoadListener;
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
        }

        private void bindGalleryItem(ImageItem imageItem) {
            name.setText(imageItem.getName());
            description.setText(imageItem.getDescription());
            reference.setText(imageItem.getReference());
            price.setText(imageItem.getPrice());
            description.setText(imageItem.getDescription());

            picasso.load(imageItem.getId_default_image()).
                    resize(70, 80).
                    into(default_image);
        }
    }

    private List<ImageItem> items;
    private LoadListener listener;


    public PrestaAdapter(Context context, List<ImageItem> items){ //,LoadListener listener){
        this.mContext = context;
        this.items = items;
//        this.listener = listener;
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
    public void onBindViewHolder(PrestaViewHolder prestaViewHolder, int i) {
        ImageItem imageItem = items.get(i);
        prestaViewHolder.bindGalleryItem(imageItem);
        if(i<items.size()){
            loading = true;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

