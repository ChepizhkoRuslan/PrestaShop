package com.chepizhko.prestashop;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.chepizhko.prestashop.model.ImageItem;


public class PrestaActivity extends SingleFragmentActivity {

    private static final String ARG_IMAGE_ITEM = "image_item";

    public static Intent newIntent(Context packageContext, ImageItem imageItem) {
        Intent intent = new Intent(packageContext, PrestaActivity.class);
        intent.putExtra(ARG_IMAGE_ITEM, imageItem);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        ImageItem imageItem = (ImageItem) getIntent().getParcelableExtra(ARG_IMAGE_ITEM);
        return PrestaFragment.newInstance(imageItem);
    }
}
