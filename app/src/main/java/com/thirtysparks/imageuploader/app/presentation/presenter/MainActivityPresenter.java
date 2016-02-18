package com.thirtysparks.imageuploader.app.presentation.presenter;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.thirtysparks.imageuploader.app.presentation.ui.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 16/2/2016.
 */
public class MainActivityPresenter implements BasePresenter {
    private MainView view;

    public MainActivityPresenter(){
    }

    @Override
    public void destroy() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void setView(MainView view){
        this.view = view;
    }

    public void processPickImageResult(Intent data){
        List<Uri> imagesList = extractSelectedImage(data);
        view.displaySelectedImages(imagesList);
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
