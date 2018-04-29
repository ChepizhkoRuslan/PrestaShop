package com.chepizhko.prestashop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chepizhko.prestashop.model.ImageItem;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class PrestaFragment extends Fragment {
    private static final String ARG_IMAGE_ITEM = "image_item";
    View v;
    private ImageView image;
    private TextView name;
    private TextView price;
    private TextView desc;
    private TextView refer;


    public static PrestaFragment newInstance(ImageItem imageItem) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_IMAGE_ITEM, imageItem);
        PrestaFragment fragment = new PrestaFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.presta_fragment, container, false);
        image = (ImageView) v.findViewById(R.id.presta_image);
        name = (TextView) v.findViewById(R.id.presta_name);
        price = (TextView) v.findViewById(R.id.presta_price);
        desc = (TextView) v.findViewById(R.id.presta_description);
        refer = (TextView) v.findViewById(R.id.presta_reference);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageItem imageItem = (ImageItem) getArguments().getParcelable(ARG_IMAGE_ITEM);
        name.setText(imageItem.getName());
        desc.setText(imageItem.getDescription());
        refer.setText(imageItem.getReference());
        price.setText(imageItem.getPrice());
        Picasso picasso = new Picasso.Builder(getContext())
                .downloader(new OkHttp3Downloader(App.getClient())).build();

        picasso.load(imageItem.getId_default_image()).
                resize(210, 240).
                into(image);

    }
}
