package com.chepizhko.prestashop.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chepizhko.prestashop.R;
import com.chepizhko.prestashop.model.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PrestaAdapter extends RecyclerView.Adapter<PrestaAdapter.PrestaViewHolder> {

    private Context mContext;

    public class PrestaViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private ImageView default_image;
        private TextView name;
        private TextView description;
        private TextView reference;
        private TextView price;


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
            Picasso.with(mContext)
                    .load(imageItem.getId_default_image())
                    //.placeholder(R.drawable.bill_up_close)
                    .into(default_image);
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
    public void onBindViewHolder(PrestaViewHolder prestaViewHolder, int i) {
        ImageItem imageItem = items.get(i);
        prestaViewHolder.bindGalleryItem(imageItem);

//        prestaViewHolder.name.setText(items.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

