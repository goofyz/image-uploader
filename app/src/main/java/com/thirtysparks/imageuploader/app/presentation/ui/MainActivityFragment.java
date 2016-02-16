package com.thirtysparks.imageuploader.app.presentation.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.thirtysparks.imageuploader.app.R;
import com.thirtysparks.imageuploader.app.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final int  FILE_CODE = 0;

    @Bind(R.id.btn_select) TextView textView;
    @Bind(R.id.ll_images) LinearLayout imagesLinearLayout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This always works
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");
                getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");

                startActivityForResult(chooserIntent, FILE_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            processPickImageResult(data);
        }
    }

    private void processPickImageResult(Intent data){
        List<Uri> imagesList = extractSelectedImage(data);

        for(Uri uri:imagesList){
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.convertDipToPix(getResources().getDisplayMetrics(), 200));
            params.bottomMargin = Util.convertDipToPix(getResources().getDisplayMetrics(), 8);
            iv.setLayoutParams(params);
            Glide.with(this)
                    .load(uri)
                    .into(iv);
            imagesLinearLayout.addView(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private List<Uri> extractSelectedImage(Intent data){
        List<Uri> imagesList = new ArrayList<>();
        if(data.getData()!=null){
            Uri mImageUri=data.getData();

            Log.v("AAA", "One image: " + mImageUri);
            imagesList.add(mImageUri);

        }
        else {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagesList.add(uri);
                    Log.v("LOG_TAG", "image: " + uri);
                }
            }
        }
        return imagesList;
    }
}
